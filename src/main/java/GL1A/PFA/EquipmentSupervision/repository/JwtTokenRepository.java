package GL1A.PFA.EquipmentSupervision.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import GL1A.PFA.EquipmentSupervision.model.Authentification;

public interface JwtTokenRepository extends JpaRepository<Authentification,Long>{
    @Query(value = """
            select t.idtoken,t.expired,t.jwttoken,t.user_id from authentification t inner join credentials u\s
            on t.user_id = u.userid\s
            where u.userid = :id and t.expired = false\s
            """, nativeQuery = true)
    List<Authentification> findAllValidTokenByUser(long id);

    Optional<Authentification> findByJwttoken(String token);
}
