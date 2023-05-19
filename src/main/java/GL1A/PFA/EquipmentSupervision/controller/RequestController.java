package GL1A.PFA.EquipmentSupervision.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import GL1A.PFA.EquipmentSupervision.communication.DemandedRequest;
import GL1A.PFA.EquipmentSupervision.communication.SentRequest;
import GL1A.PFA.EquipmentSupervision.model.Credentials;
import GL1A.PFA.EquipmentSupervision.model.Resultat;
import GL1A.PFA.EquipmentSupervision.service.RequestService;

@RestController
public class RequestController {

    @Autowired
    RequestService service;

    @PostMapping("/employee/add-request")
    @ResponseBody
    void addRequest(@AuthenticationPrincipal Credentials credentials,@RequestBody DemandedRequest dRequest){
        service.addRequest(credentials, dRequest);
    }

    @GetMapping("/employee/get-requests")
    @ResponseBody
    List<SentRequest> getEmployeeRequests(@AuthenticationPrincipal Credentials credentials){
        return service.getEmployeeRequests(credentials);
    }

    @GetMapping("/admin/get-requests")
    @ResponseBody
    List<SentRequest> getAllRequests(){
        return service.getAllRequests();
    }

    @PostMapping("/admin/send-response")
    @ResponseBody
    void SendResponse(@RequestParam long numreq,@RequestParam Resultat resultat){
        service.SendResponse(numreq, resultat);
    }
}
