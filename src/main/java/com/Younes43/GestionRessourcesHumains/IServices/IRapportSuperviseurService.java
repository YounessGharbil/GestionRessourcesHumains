package com.Younes43.GestionRessourcesHumains.IServices;

import com.Younes43.GestionRessourcesHumains.Entities.Demande_Sanction.RAPPORT_SUPERVISEUR;

import javax.mail.MessagingException;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.HashMap;

public interface IRapportSuperviseurService {
     RAPPORT_SUPERVISEUR createRapportSuperviseur(RAPPORT_SUPERVISEUR rapportSuperviseur,
                                                        HashMap<String,String> headers)
            throws MessagingException, GeneralSecurityException, IOException;
     RAPPORT_SUPERVISEUR getRapportSuperviseur(Long id);
}
