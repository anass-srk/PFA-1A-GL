package GL1A.PFA.EquipmentSupervision.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import GL1A.PFA.EquipmentSupervision.model.Credentials;

public interface CredentialsRepository extends JpaRepository<Credentials,Long>{
    Optional<Credentials> findByEmail(String email);
    boolean existsByEmail(String email);
}
