package GL1A.PFA.EquipmentSupervision.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import GL1A.PFA.EquipmentSupervision.communication.EquipementRequest;
import GL1A.PFA.EquipmentSupervision.communication.EquipementResponse;
import GL1A.PFA.EquipmentSupervision.model.Credentials;
import GL1A.PFA.EquipmentSupervision.service.EquipementService;

@RestController
public class EquipemetController {

    @Autowired
    EquipementService service;
    
    @PostMapping("/admin/add-equipement")
    @ResponseBody
    void addEquipement(@RequestBody EquipementRequest request){
        service.addEquipement(request);
    }

    @PostMapping("/admin/remove-equipement")
    @ResponseBody
    void removeEquipement(@RequestParam long id){
        service.removeEquipement(id);
    }

    @PostMapping("/admin/change-equipement")
    @ResponseBody
    void changeEquipement(@RequestParam long numser,@RequestBody Map<String,String> changes){
        service.changeEquipement(numser,changes);
    }

    @GetMapping("/admin/get-equipements")
    @ResponseBody
    List<EquipementResponse> getAllEquipement(){
        return service.getAllEquipement();
    }

    @GetMapping("/employee/get-equipements")
    @ResponseBody
    List<EquipementResponse> getEquipements(@AuthenticationPrincipal Credentials credentials) {
        return service.getEquipements(credentials);
    }

    @PostMapping("/employee/assign-equipements")
    @ResponseBody
    void assignEquipment(@AuthenticationPrincipal Credentials credentials){
        service.assignEquipment(credentials.getUser_id());
    }
}
