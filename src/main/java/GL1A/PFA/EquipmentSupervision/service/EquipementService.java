package GL1A.PFA.EquipmentSupervision.service;

import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;

import GL1A.PFA.EquipmentSupervision.communication.EquipementRequest;
import GL1A.PFA.EquipmentSupervision.communication.EquipementResponse;
import GL1A.PFA.EquipmentSupervision.model.Credentials;
import GL1A.PFA.EquipmentSupervision.model.Equipement;
import GL1A.PFA.EquipmentSupervision.model.EquipementEtat;
import GL1A.PFA.EquipmentSupervision.model.EquipementMarque;
import GL1A.PFA.EquipmentSupervision.model.EquipementType;
import GL1A.PFA.EquipmentSupervision.model.UserRole;
import GL1A.PFA.EquipmentSupervision.model.Utilisateur;
import GL1A.PFA.EquipmentSupervision.repository.EquipementRepository;
import GL1A.PFA.EquipmentSupervision.repository.UserRepository;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class EquipementService {
    @Autowired
    EquipementRepository equipementRepository;

    @Autowired
    UserRepository userRepository;

    boolean userHasEquipementType(Utilisateur user,EquipementType type){
        return user.getEquipements().stream().map(e -> e.getType())
        .toList().contains(type);
    }

    //Remark : if no employee uses the machine user_id must be 0
    public void addEquipement(EquipementRequest equipementRequest){
        var val = equipementRequest.getUser_id();
        var user = userRepository.findById(val).orElse(null);
        if (user != null && (user.getEquipements().size() > EquipementType.values().length
        || userHasEquipementType(user,equipementRequest.getType()))) {
            throw new IllegalStateException("A user can't have more than 3 equipements or more equipements of the same type !");
        }
        if(user != null && user.getRole() == UserRole.SUPERVISOR){
            throw new IllegalStateException("Cannot assign equipment to supervisor !");
        }
        Equipement equipement = Equipement.builder()
        .type(equipementRequest.getType())
        .etat(equipementRequest.getEtat())
        .marque(equipementRequest.getMarque())
        .dateachat(equipementRequest.getDateachat())
        .etage(equipementRequest.getEtage())
        .salle(equipementRequest.getSalle())
        .build();
        if(user != null){
            equipement.setUserid(user);
        }
        equipementRepository.save(equipement);
    }

    public void removeEquipement(long numser){
        if(!equipementRepository.findById(numser).isPresent()){
            throw new IllegalStateException(
                String.format("No equipement has numser %d !",numser)
                );
        }
        equipementRepository.deleteById(numser);
    }

    public void changeEquipement(long numser,Map<String,String> changes){
        Equipement equipement = equipementRepository.findById(numser).orElseThrow(
            () -> new IllegalStateException(
                String.format("No equipement has numser %d !",numser)
                )
        );
        if(changes.containsKey("type")){
            var val = changes.remove("type");
            if(!List.of(EquipementType.values()).contains(EquipementType.valueOf(val))){
                throw new IllegalStateException(String.format("No such equipement type (%S)!",val));
            }
            equipement.setType(EquipementType.valueOf(val));
        }
        if (changes.containsKey("etat")) {
            var val = changes.remove("etat");
            if (!List.of(EquipementEtat.values()).contains(EquipementEtat.valueOf(val))) {
                throw new IllegalStateException(String.format("No such equipement etat (%S)!", val));
            }
            equipement.setEtat(EquipementEtat.valueOf(val));
        }
        if (changes.containsKey("marque")){
            var val = changes.remove("marque");
            if (!List.of(EquipementMarque.values()).contains(EquipementMarque.valueOf(val))) {
                throw new IllegalStateException(String.format("No such equipement marque (%S)!", val));
            }
            equipement.setMarque(EquipementMarque.valueOf(val));
        }
        if(changes.containsKey("dateachat")){
            equipement.setDateachat(Date.valueOf(changes.remove("dateachat")));
        }
        if(changes.containsKey("etage")){
            equipement.setEtage(Short.parseShort(changes.remove("etage")));
        }
        if (changes.containsKey("salle")) {
            equipement.setSalle(Short.parseShort(changes.remove("salle")));
        }
        if(changes.containsKey("user_id")){
            if(changes.get("user_id").equals("null") 
            || Long.parseLong(changes.get("user_id")) == 0){
                if(equipement.getUserid() != null){
                    var old_user = equipement.getUserid();
                    old_user.getEquipements().remove(equipement);
                    equipement.setUserid(null);
                    userRepository.save(old_user);
                    equipementRepository.save(equipement);
                }
                changes.remove("user_id");
            }else{
            var val = Long.parseLong(changes.remove("user_id"));
            var user = userRepository.findById(val).orElseThrow(
            () -> new IllegalStateException(String.format(
                "No user has id %d !", val))
            );
            if(user.getRole() == UserRole.SUPERVISOR){
                throw new IllegalStateException("Cannot assign equipment to supervisor !");
            }
            if(equipement.getUserid() != null){
                var old_user = equipement.getUserid();
                old_user.getEquipements().remove(equipement);
            }
            if (user.getEquipements().size() > EquipementType.values().length
            || userHasEquipementType(user,equipement.getType())) {
            throw new IllegalStateException("A user can't have more than 3 equipements or more equipements of the same type !");
            }
            user.getEquipements().add(equipement);
            equipement.setUserid(user);
            if (equipement.getUserid() != null){
                userRepository.save(equipement.getUserid());
            }
            userRepository.save(user);
            }
        }
        if(changes.size() != 0){
            throw new IllegalStateException("Unknown equipment changes !");
        }
        equipementRepository.save(equipement);
    }

    public List<EquipementResponse> getAllEquipement(){
        return equipementRepository.findAll()
        .stream().map(EquipementResponse::fromEquipement).toList();
    }

    public List<EquipementResponse> getEquipements(@AuthenticationPrincipal Credentials credentials){
        return equipementRepository.findByUserid(credentials.getUserid())
        .stream().map(EquipementResponse::fromEquipement).toList();
    }

    public void assignEquipment(long id) {
        Utilisateur user = userRepository.findById(id).orElseThrow(
                () -> new IllegalStateException(String.format(
                        "No user has id %d !", id)));
        List<Equipement> equipements = equipementRepository.findByUserid(user);
        List<EquipementType> types = List.of(EquipementType.values());
        for (var equipement : equipements) {
            types.remove(equipement.getType());
        }
        if (types.size() == 0) {
            return;
        }
        List<Equipement> rest = equipementRepository.findByUseridIsNull();
        Map<EquipementType, Equipement> map = new HashMap<>();
        for (var equipement : rest) {
            if (types.contains(equipement.getType()) && !map.containsKey(equipement.getType())
                    && equipement.getEtat() == EquipementEtat.FULLY_FUNCTIONING) {
                map.put(equipement.getType(), equipement);
            }
        }
        List<Equipement> newlyAssigned = map.values().stream().toList();
        newlyAssigned.forEach(eq -> eq.setUserid(user));
        user.getEquipements().addAll(newlyAssigned);
        equipementRepository.saveAll(newlyAssigned);
    }
}

