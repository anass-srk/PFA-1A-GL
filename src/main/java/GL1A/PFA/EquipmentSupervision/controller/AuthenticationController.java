package GL1A.PFA.EquipmentSupervision.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import GL1A.PFA.EquipmentSupervision.communication.AuthenticationRequest;
import GL1A.PFA.EquipmentSupervision.communication.AuthenticationResponse;
import GL1A.PFA.EquipmentSupervision.communication.RegisterRequest;
import GL1A.PFA.EquipmentSupervision.service.AuthenticationService;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {
    @Autowired
    AuthenticationService service;

    @PostMapping("/login")
    @ResponseBody
    public AuthenticationResponse login(@RequestBody AuthenticationRequest request){
        return service.authenticate(request);
    }

    @PostMapping("/register")
    @ResponseBody
    public void register(@RequestBody RegisterRequest request){
        service.register(request);
    }
}
