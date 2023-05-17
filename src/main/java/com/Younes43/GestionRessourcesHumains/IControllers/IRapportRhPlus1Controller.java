package com.Younes43.GestionRessourcesHumains.IControllers;
import java.util.List;


import com.Younes43.GestionRessourcesHumains.Entities.Requests.CreateRapportRhPlus1Request;
import com.Younes43.GestionRessourcesHumains.Entities.Responses.GetAllRapportRhPlus1Response;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import javax.mail.MessagingException;
import java.io.IOException;
import java.security.GeneralSecurityException;

public interface IRapportRhPlus1Controller {

    ResponseEntity<String> createRapportRhPlus1(HttpServletRequest request,
                                                @RequestBody CreateRapportRhPlus1Request createRapportRhPlus1Request) throws MessagingException, GeneralSecurityException, IOException;

ResponseEntity<List<GetAllRapportRhPlus1Response>> getAllRapportRhPlus1();
ResponseEntity<GetAllRapportRhPlus1Response> getRapportRhPlus1(Long id);
// ResponseEntity<GetAllRapportRhPlus1Response> getRapportRhPlus1ByDemandeDeSanctionId(Long id);


}