package com.Younes43.GestionRessourcesHumains.IServices;

import com.Younes43.GestionRessourcesHumains.Entities.Demande_Sanction.RAPPORT_TEAM_LEADER;

import javax.mail.MessagingException;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.HashMap;
import java.util.List;

public interface IRapportTeamLeaderService {
     RAPPORT_TEAM_LEADER createRapportTeamLeader(RAPPORT_TEAM_LEADER rapportTeamLeader,
                                                       HashMap<String,String> headers)
            throws MessagingException, GeneralSecurityException, IOException;

     RAPPORT_TEAM_LEADER getRapportTeamLeader(Long id);
     // RAPPORT_TEAM_LEADER getRapportTeamLeaderByDemandeDeSanctionId(Long id);
     List<RAPPORT_TEAM_LEADER> getAllRapportTeamLeader();
     List<RAPPORT_TEAM_LEADER> getRapportsTeamLeaderNotProcessedBySperviseur();
}
