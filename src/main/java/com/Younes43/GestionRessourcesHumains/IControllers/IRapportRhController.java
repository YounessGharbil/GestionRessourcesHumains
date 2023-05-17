package com.Younes43.GestionRessourcesHumains.IControllers;
import java.util.List;


import com.Younes43.GestionRessourcesHumains.Entities.Requests.CreateRapportRhRequest;
import com.Younes43.GestionRessourcesHumains.Entities.Responses.GetAllRapportRhResponse;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import javax.mail.MessagingException;
import java.io.IOException;
import java.security.GeneralSecurityException;

public interface IRapportRhController {

    ResponseEntity<String> createRapportRh(HttpServletRequest request,
                                           @RequestBody CreateRapportRhRequest createRapportRhRequest) throws MessagingException, GeneralSecurityException, IOException;

ResponseEntity<List<GetAllRapportRhResponse>> getAllRapportRh();
ResponseEntity<GetAllRapportRhResponse> getRapportRh(Long id);
// ResponseEntity<GetAllRapportRhResponse> getRapportRhByDemandeDeSanctionId(Long id);


}