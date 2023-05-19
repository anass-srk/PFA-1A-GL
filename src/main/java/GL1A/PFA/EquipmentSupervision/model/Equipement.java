package GL1A.PFA.EquipmentSupervision.model;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "equipement")
@Entity
public class Equipement {
    @Id
    @GeneratedValue
    @Column(name = "numser")
    private long numser;    

    @Column(name = "type",nullable = false)
    private EquipementType type;

    @Column(name = "Etat", nullable = false)
    private EquipementEtat etat;

    @Column(name = "marque", nullable = false)
    private EquipementMarque marque;

    @Column(name = "dateachat",nullable = false)
    private Date dateachat;

    @Column(name = "etage",nullable = false)
    private short etage;

    @Column(name = "salle",nullable = false)
    private short salle;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userid")
    @JsonBackReference
    public Utilisateur userid;

    @OneToMany(mappedBy = "equipementid")
    @JsonManagedReference
    @JsonIgnore
    private List<Request> request;
}
