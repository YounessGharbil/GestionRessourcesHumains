package com.Younes43.GestionRessourcesHumains.IServices;

import com.Younes43.GestionRessourcesHumains.Entities.Demande_Sanction.RAPPORT_MANAGER;

import javax.mail.MessagingException;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.HashMap;
import java.util.List;

public interface IRapportManagerService {
     RAPPORT_MANAGER createRapportManager(RAPPORT_MANAGER rapportManager,
                                                HashMap<String,String> headers ) throws MessagingException, GeneralSecurityException, IOException;
     RAPPORT_MANAGER getRapportManager(Long id);
     // RAPPORT_MANAGER getRapportManagerByDemandeDeSanctionId(Long id);
     List<RAPPORT_MANAGER> getAllRapportManager();
}
