package GL1A.PFA.EquipmentSupervision.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Authentification")
@Entity
public class Authentification {

    @Id
    @GeneratedValue
    @Column(name = "idTOKEN")
    public long idtoken;

    @Column(name = "jwtTOKEN",unique = true)
    public String jwttoken;

    @Column(name = "expired")
    public boolean expired;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonBackReference
    public Credentials credentials;
}
