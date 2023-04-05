package com.Younes43.GestionRessourcesHumains.IControllers;

import com.Younes43.GestionRessourcesHumains.Entities.Requests.CreateRapportSuperviseurRequest;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import javax.mail.MessagingException;
import java.io.IOException;
import java.security.GeneralSecurityException;

public interface IRapportSuperviseurController {

    ResponseEntity<String> createRapportSuperviseur(HttpServletRequest request,
                                                    @RequestBody CreateRapportSuperviseurRequest createRapportSuperviseurRequest)
                                            throws MessagingException, GeneralSecurityException, IOException;
}
