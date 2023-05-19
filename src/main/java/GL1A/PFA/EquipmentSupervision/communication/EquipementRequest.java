package GL1A.PFA.EquipmentSupervision.communication;

import java.util.Date;

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
public class EquipementRequest {
    private EquipementType type;

    private EquipementEtat etat;

    private EquipementMarque marque;

    private Date dateachat;

    private short etage;

    private short salle;

    public long user_id;
}
