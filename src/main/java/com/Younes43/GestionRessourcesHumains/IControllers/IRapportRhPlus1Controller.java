package com.Younes43.GestionRessourcesHumains.IControllers;

import com.Younes43.GestionRessourcesHumains.Entities.Requests.CreateRapportRhPlus1Request;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import javax.mail.MessagingException;
import java.io.IOException;
import java.security.GeneralSecurityException;

public interface IRapportRhPlus1Controller {

    ResponseEntity<String> createRapportRhPlus1(HttpServletRequest request,
                                                @RequestBody CreateRapportRhPlus1Request createRapportRhPlus1Request) throws MessagingException, GeneralSecurityException, IOException;
}
