package GL1A.PFA.EquipmentSupervision.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import GL1A.PFA.EquipmentSupervision.model.Equipement;
import GL1A.PFA.EquipmentSupervision.model.Utilisateur;

import java.util.List;


public interface EquipementRepository extends JpaRepository<Equipement,Long>{
    List<Equipement> findByUserid(Utilisateur user);
    List<Equipement> findByUseridIsNull();
}
