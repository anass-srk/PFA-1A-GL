package GL1A.PFA.EquipmentSupervision.communication;

import java.util.Date;

import GL1A.PFA.EquipmentSupervision.model.Equipement;
import GL1A.PFA.EquipmentSupervision.model.EquipementEtat;
import GL1A.PFA.EquipmentSupervision.model.EquipementMarque;
import GL1A.PFA.EquipmentSupervision.model.EquipementType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EquipementResponse {
    private long numser;

    private EquipementType type;

    private EquipementEtat etat;

    private EquipementMarque marque;

    private Date dateachat;

    private short etage;

    private short salle;

    public long user_id;

    public static EquipementResponse fromEquipement(Equipement equipement){
         var response = EquipementResponse.builder()
        .numser(equipement.getNumser())
        .etat(equipement.getEtat())
        .type(equipement.getType())
        .marque(equipement.getMarque())
        .dateachat(equipement.getDateachat())
        .etage(equipement.getEtage())
        .salle(equipement.getSalle())
        .build();
        if(equipement.getUserid() != null){
            response.setUser_id(equipement.getUserid().getId());
        }
        return response;
    }
}
