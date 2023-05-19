package GL1A.PFA.EquipmentSupervision.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Utilisateurs")
@Entity
public class Utilisateur {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private long id;

    @Column(name = "prenom",nullable = false)
    private String prenom;

    @Column(name = "nom",nullable = false)
    private String nom;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "role",nullable = false)
    private UserRole role;

    @Column(name = "departement")
    private String departement;

    @Column(name = "etage")
    private short etage;

    @Column(name = "salle")
    private short salle;

    @OneToOne(mappedBy = "userid",cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private Credentials credentials;

    @OneToMany(mappedBy = "userid")
    @JsonManagedReference
    @JsonIgnore
    private List<Equipement> Equipements;

    @OneToMany(mappedBy = "userid",fetch = FetchType.EAGER)
    @JsonManagedReference
    @JsonIgnore
    private List<Request> request_employee;

    @OneToMany(mappedBy = "adminid")
    @JsonManagedReference
    @JsonIgnore
    private List<Request> request_admin;
}
