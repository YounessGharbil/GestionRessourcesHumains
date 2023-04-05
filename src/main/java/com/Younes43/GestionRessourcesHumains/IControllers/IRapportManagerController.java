package com.Younes43.GestionRessourcesHumains.IControllers;

import com.Younes43.GestionRessourcesHumains.Entities.Requests.CreateRapportManagerRequest;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import javax.mail.MessagingException;
import java.io.IOException;
import java.security.GeneralSecurityException;

public interface IRapportManagerController {

    ResponseEntity<String> createRapportManager(HttpServletRequest request,
                                                @RequestBody CreateRapportManagerRequest createRapportManagerRequest) throws MessagingException, GeneralSecurityException, IOException;
}
