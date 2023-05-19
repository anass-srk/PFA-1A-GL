package GL1A.PFA.EquipmentSupervision.communication;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DemandedRequest {

    private String commentaire;

    private long numser;

}
