package com.Younes43.GestionRessourcesHumains.IControllers;
import java.util.List;


import com.Younes43.GestionRessourcesHumains.Entities.Requests.CreateRapportSuperviseurRequest;
import com.Younes43.GestionRessourcesHumains.Entities.Responses.GetAllRapportSuperviseurResponse;

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

    ResponseEntity<List<GetAllRapportSuperviseurResponse>> getAllRapportSuperviseur();
    ResponseEntity<GetAllRapportSuperviseurResponse> getRapportSuperviseur(Long id);
    ResponseEntity<List<GetAllRapportSuperviseurResponse>> getRapportsSuperviseurNotProcessedByManager();

}
