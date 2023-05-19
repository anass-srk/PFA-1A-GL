package GL1A.PFA.EquipmentSupervision.communication;

import GL1A.PFA.EquipmentSupervision.model.Request;
import GL1A.PFA.EquipmentSupervision.model.Resultat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SentRequest {
    private long numreq;

    private String commentaire;

    private long numser;

    private long user_id;

    private long admin_id;

    private Resultat resultat;

    public static SentRequest fromRequest(Request request){
        var toSend = SentRequest.builder()
        .numreq(request.getNumreq())
        .commentaire(request.getCommentaire())
        .numser(request.getEquipementid().getNumser())
        .user_id(request.getUserid().getId())
        .resultat(request.getResultat())
        .build();
        if(request.getAdminid() != null){
            toSend.setAdmin_id(request.getAdminid().getId());
        }
        return toSend;
    }
}
