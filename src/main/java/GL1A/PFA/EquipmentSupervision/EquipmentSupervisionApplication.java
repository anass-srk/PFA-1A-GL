package GL1A.PFA.EquipmentSupervision;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import GL1A.PFA.EquipmentSupervision.communication.EquipementRequest;
import GL1A.PFA.EquipmentSupervision.model.Credentials;
import GL1A.PFA.EquipmentSupervision.model.EquipementEtat;
import GL1A.PFA.EquipmentSupervision.model.EquipementMarque;
import GL1A.PFA.EquipmentSupervision.model.EquipementType;
import GL1A.PFA.EquipmentSupervision.model.UserRole;
import GL1A.PFA.EquipmentSupervision.model.Utilisateur;
import GL1A.PFA.EquipmentSupervision.repository.JwtTokenRepository;
import GL1A.PFA.EquipmentSupervision.repository.CredentialsRepository;
import GL1A.PFA.EquipmentSupervision.repository.UserRepository;
import GL1A.PFA.EquipmentSupervision.service.EquipementService;
import jakarta.transaction.Transactional;
import net.datafaker.Faker;

@SpringBootApplication
@Transactional
public class EquipmentSupervisionApplication implements CommandLineRunner{
	@Autowired
	UserRepository userRepository;

	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
	CredentialsRepository credentialsRepository;

	@Autowired
	EquipementService equipementService;

	@Autowired
	JwtTokenRepository jwtTokenRepository;

	public static void main(String[] args) {
		SpringApplication.run(EquipmentSupervisionApplication.class, args);
	}

	Utilisateur createUser(String prenom,String nom,String email,String password,UserRole role,String dep){
		if(credentialsRepository.existsByEmail(email)){
			return credentialsRepository.findByEmail(email).get().getUserid();
		}
		Credentials credentials = Credentials.builder()
		.email(email)
		.password(passwordEncoder.encode(password))
		.build();

		Utilisateur user = Utilisateur.builder()
		.prenom(prenom)
		.nom(nom)
		.role(role)
		.departement(dep)
		.credentials(credentials)
		.build();

		credentials.setUserid(user);
		return userRepository.save(user);
	}

	@Override
	public void run(String... args) throws Exception {
		createUser("admin", "admin", "admin@admin.com", "admin", UserRole.SUPERVISOR,"");
		createUser("Anass","Serroukh", "anass@email.com", "null", UserRole.EMPLOYEE, "main");
		Faker faker = new Faker();
		List<EquipementRequest> equipement = faker.<EquipementRequest>collection(
			() -> EquipementRequest.builder()
			.type(EquipementType.values()[faker.random().nextInt(0,EquipementType.values().length-1)])
			.etat(EquipementEtat.values()[faker.random().nextInt(0,EquipementEtat.values().length-1)])
			.marque(EquipementMarque.values()[faker.random().nextInt(0,EquipementMarque.values().length-1)])
			.dateachat(new java.util.Date())
			.etage(faker.random().nextInt(1, 5).shortValue())
			.salle(faker.random().nextInt(1, 5).shortValue())
			.build()
		).len(3,8).generate();
		equipement.forEach(e -> equipementService.addEquipement(e));

	}

}
