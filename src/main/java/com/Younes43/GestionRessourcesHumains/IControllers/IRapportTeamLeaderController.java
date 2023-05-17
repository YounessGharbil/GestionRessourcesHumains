package com.Younes43.GestionRessourcesHumains.IControllers;
import com.Younes43.GestionRessourcesHumains.Entities.Demande_Sanction.RAPPORT_TEAM_LEADER;


import com.Younes43.GestionRessourcesHumains.Entities.Requests.CreateRapportTeamLeaderRequest;
import com.Younes43.GestionRessourcesHumains.Entities.Responses.GetAllRapportTeamLeaderResponse;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import javax.mail.MessagingException;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

public interface IRapportTeamLeaderController {

    ResponseEntity<String> createRapportTeamLeader(HttpServletRequest request,
                                                   @RequestBody CreateRapportTeamLeaderRequest createRapportTeamLeaderRequest) throws MessagingException, GeneralSecurityException, IOException;
    ResponseEntity<List<GetAllRapportTeamLeaderResponse>> getAllRapportTeamLeader();
    ResponseEntity<GetAllRapportTeamLeaderResponse> getRapportTeamLeader(Long id);
    ResponseEntity<List<GetAllRapportTeamLeaderResponse>> getRapportsTeamLeaderNotProcessedByManager();
}
