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

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Request")
@Entity
public class Request {
    @Id
    @GeneratedValue
    @Column(name = "numreq")
    private long numreq;

    @Column(name = "commentaire")
    private String commentaire;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "equipementid",referencedColumnName = "NumSer")
    @JsonBackReference
    private Equipement equipementid;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "userid", referencedColumnName = "id")
    @JsonBackReference
    private Utilisateur userid;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "adminid", referencedColumnName = "id")
    @JsonBackReference
    private Utilisateur adminid;

    @Column(name = "Resultat",nullable = false)
    private Resultat resultat;
}
