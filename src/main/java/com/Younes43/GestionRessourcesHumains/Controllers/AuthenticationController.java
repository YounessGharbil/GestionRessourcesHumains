package com.Younes43.GestionRessourcesHumains.Controllers;

import com.Younes43.GestionRessourcesHumains.Entities.Requests.AuthenticationRequest;
import com.Younes43.GestionRessourcesHumains.Entities.Responses.AuthenticationResponse;
import com.Younes43.GestionRessourcesHumains.IControllers.IAuthenticationController;
import com.Younes43.GestionRessourcesHumains.Services.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins ="http://localhost:4200")
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController implements IAuthenticationController {
    private final AuthenticationService authenticationService;
    @Override
    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request){
        return new ResponseEntity<>(authenticationService.authenticate(request), HttpStatus.OK);
    }
}
