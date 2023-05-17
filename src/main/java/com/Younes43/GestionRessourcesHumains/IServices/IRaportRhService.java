package com.Younes43.GestionRessourcesHumains.IServices;

import com.Younes43.GestionRessourcesHumains.Entities.Demande_Sanction.RAPPORT_RH;

import javax.mail.MessagingException;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.HashMap;
import java.util.List;

public interface IRaportRhService {
     RAPPORT_RH createRapportRh(RAPPORT_RH rapportRh,
                                      HashMap<String,String> headers) throws MessagingException, GeneralSecurityException, IOException ;
     RAPPORT_RH getRapportRh(Long id);
     // RAPPORT_RH getRapportRhByDemandeDeSanctionId(Long id);
     List<RAPPORT_RH> getAllRapportRh();
    }
