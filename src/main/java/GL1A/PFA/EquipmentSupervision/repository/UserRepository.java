package GL1A.PFA.EquipmentSupervision.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import GL1A.PFA.EquipmentSupervision.model.Utilisateur;

public interface UserRepository extends JpaRepository<Utilisateur,Long>{
    
}
