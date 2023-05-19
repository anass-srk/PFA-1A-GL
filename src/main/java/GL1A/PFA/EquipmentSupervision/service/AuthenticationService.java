package GL1A.PFA.EquipmentSupervision.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import GL1A.PFA.EquipmentSupervision.communication.AuthenticationRequest;
import GL1A.PFA.EquipmentSupervision.communication.AuthenticationResponse;
import GL1A.PFA.EquipmentSupervision.communication.RegisterRequest;
import GL1A.PFA.EquipmentSupervision.model.Authentification;
import GL1A.PFA.EquipmentSupervision.model.Credentials;
import GL1A.PFA.EquipmentSupervision.model.UserRole;
import GL1A.PFA.EquipmentSupervision.model.Utilisateur;
import GL1A.PFA.EquipmentSupervision.repository.CredentialsRepository;
import GL1A.PFA.EquipmentSupervision.repository.JwtTokenRepository;
import GL1A.PFA.EquipmentSupervision.repository.UserRepository;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class AuthenticationService {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private CredentialsRepository credentialsRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtTokenRepository tokenRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Value("${app.verification_token.life}")
    private long interval;
    
    public void register(RegisterRequest request){
        if(credentialsRepository.existsByEmail(request.getEmail())){
            throw new IllegalStateException("The email " + request.getEmail() + " is taken !");
        }

        Credentials credentials = Credentials.builder()
        .email(request.getEmail())
        .password(passwordEncoder.encode(request.getPassword()))
        .build();

        Utilisateur utilisateur = Utilisateur.builder()
        .prenom(request.getPrenom())
        .nom(request.getNom())
        .departement(request.getDepartement())
        .role(UserRole.EMPLOYEE)
        .credentials(credentials)
        .etage(request.getEtage())
        .salle(request.getSalle())
        .build();

        credentials.setUserid(utilisateur);

        userRepository.save(utilisateur);
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()));
        var credentials = credentialsRepository.findByEmail(request.getEmail())
                .orElseThrow();
        var jwtToken = jwtService.generateToken(credentials);
        revokeAllUserTokens(credentials);
        saveUserToken(credentials, jwtToken);
        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .build();
    }

    private void saveUserToken(Credentials credentials, String jwtToken) {
        var token = Authentification.builder()
                .credentials(credentials)
                .jwttoken(jwtToken)
                .expired(false)
                .build();
        tokenRepository.save(token);
    }

    private void revokeAllUserTokens(Credentials credentials) {
        var validUserTokens = tokenRepository.findAllValidTokenByUser(credentials.getUser_id());
        if (validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(token -> {
            token.setExpired(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }
}
