package com.Younes43.GestionRessourcesHumains.IServices;

import com.Younes43.GestionRessourcesHumains.Entities.DemandeSanction;
import jakarta.servlet.http.HttpServletResponse;

import javax.mail.MessagingException;
import java.io.IOException;
import java.security.GeneralSecurityException;

public interface IDemandeSanctionService {
     void generateDemande(HttpServletResponse response, DemandeSanction demandeSanction,String username,String userRole) throws IOException, MessagingException, GeneralSecurityException;
     String validateDemande(Long id,String username,String userRole) throws MessagingException, GeneralSecurityException, IOException;
}
