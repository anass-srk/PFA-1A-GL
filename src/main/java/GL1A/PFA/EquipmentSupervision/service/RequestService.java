package GL1A.PFA.EquipmentSupervision.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;

import GL1A.PFA.EquipmentSupervision.communication.DemandedRequest;
import GL1A.PFA.EquipmentSupervision.communication.SentRequest;
import GL1A.PFA.EquipmentSupervision.model.Credentials;
import GL1A.PFA.EquipmentSupervision.model.Request;
import GL1A.PFA.EquipmentSupervision.model.Resultat;
import GL1A.PFA.EquipmentSupervision.repository.CredentialsRepository;
import GL1A.PFA.EquipmentSupervision.repository.EquipementRepository;
import GL1A.PFA.EquipmentSupervision.repository.RequestRepository;
import GL1A.PFA.EquipmentSupervision.repository.UserRepository;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class RequestService {

    @Autowired
    RequestRepository repository;

    @Autowired
    CredentialsRepository credentialsRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    EquipementRepository equipementRepository;

    public void addRequest(@AuthenticationPrincipal Credentials credentials,DemandedRequest dRequest){
        var user = credentials.getUserid();
        var equipementIds = equipementRepository.findByUserid(user)
        .stream().map(e -> e.getNumser()).toList();
        if(!equipementIds.contains(dRequest.getNumser())){
            throw new IllegalStateException(String.format(
                    "No such equipment (%d) is assigned to this employee (%d)!",dRequest.getNumser(),credentials.getUser_id()));
        }
        var equipement = equipementRepository.findById(dRequest.getNumser()).get();
        Request request = Request.builder()
        .commentaire(dRequest.getCommentaire())
        .equipementid(equipement)
        .userid(user)
        .resultat(Resultat.PENDING)
        .build();

        user.getRequest_employee().add(request);
        equipement.getRequest().add(request);
        repository.save(request);
    }

    public List<SentRequest> getEmployeeRequests(@AuthenticationPrincipal Credentials credentials){
        return repository.findAll(Sort.by(Direction.DESC,"numreq")).stream()
        .filter(r -> r.getUserid().getId() == credentials.getUser_id())
        .map(SentRequest::fromRequest).toList();
    }

    public List<SentRequest> getAllRequests(){
        return repository.findAll(Sort.by(Direction.DESC,"numreq"))
        .stream().map(SentRequest::fromRequest).toList();
    }

    public void SendResponse(long numreq,Resultat resultat){
        var request = repository.findById(numreq).orElseThrow(
            () -> new IllegalStateException(String.format("No request with numreq(%d) exists !",numreq))
        );
        if(request.getResultat() != Resultat.PENDING){
            throw new IllegalStateException(String.format("This request(%d) was already processed !",numreq));
        }
        request.setResultat(resultat);
        repository.save(request);
    }
}
