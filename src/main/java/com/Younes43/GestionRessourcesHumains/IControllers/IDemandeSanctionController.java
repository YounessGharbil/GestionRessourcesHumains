package com.Younes43.GestionRessourcesHumains.IControllers;

import com.Younes43.GestionRessourcesHumains.Entities.DemandeSanction;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;

import javax.mail.MessagingException;
import java.io.IOException;
import java.security.GeneralSecurityException;


public interface IDemandeSanctionController {
     ResponseEntity<String> generateDemande(HttpServletRequest request,HttpServletResponse response, DemandeSanction demandeSanction) throws IOException, MessagingException, GeneralSecurityException;
     ResponseEntity<String> validateDemande(HttpServletRequest request,  Long id) throws MessagingException, GeneralSecurityException, IOException;
}
