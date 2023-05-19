package GL1A.PFA.EquipmentSupervision.communication;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    
    @NotNull
    @Email
    private String email;

    @NotNull
    private String prenom;

    @NotNull
    private String nom;

    private String departement;

    @Size(min = 8)
    private String password;

    private short etage;

    private short salle;
}
