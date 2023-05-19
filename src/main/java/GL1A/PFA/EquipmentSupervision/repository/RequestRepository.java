package GL1A.PFA.EquipmentSupervision.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import GL1A.PFA.EquipmentSupervision.model.Request;
import GL1A.PFA.EquipmentSupervision.model.Utilisateur;

public interface RequestRepository extends JpaRepository<Request,Long>{
    List<Request> findByUserid(Utilisateur user_id);
}
