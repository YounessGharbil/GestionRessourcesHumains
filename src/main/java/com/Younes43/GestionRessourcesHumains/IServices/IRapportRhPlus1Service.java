package com.Younes43.GestionRessourcesHumains.IServices;

import com.Younes43.GestionRessourcesHumains.Entities.Demande_Sanction.RAPPORT_RHPLUS1;

import javax.mail.MessagingException;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.HashMap;

public interface IRapportRhPlus1Service {
     RAPPORT_RHPLUS1 createRapportRhPlus1(RAPPORT_RHPLUS1 rapportRhplus1,
                                                HashMap<String,String> headers) throws MessagingException, GeneralSecurityException, IOException;
     RAPPORT_RHPLUS1 getRapportRhPlus1(Long id);
}
