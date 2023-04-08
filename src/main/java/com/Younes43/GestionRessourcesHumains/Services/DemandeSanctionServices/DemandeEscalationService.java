package com.Younes43.GestionRessourcesHumains.Services.DemandeSanctionServices;

import com.Younes43.GestionRessourcesHumains.Entities.Demande_Sanction.RAPPORT_SUPERVISEUR;
import com.Younes43.GestionRessourcesHumains.Entities.Demande_Sanction.RAPPORT_TEAM_LEADER;
import com.Younes43.GestionRessourcesHumains.Repositories.DemandeSanctionRepositories.RapportManagerRepository;
import com.Younes43.GestionRessourcesHumains.Repositories.DemandeSanctionRepositories.RapportSuperviseurRepository;
import com.Younes43.GestionRessourcesHumains.Repositories.DemandeSanctionRepositories.RapportTeamLeaderRepository;
import com.Younes43.GestionRessourcesHumains.Repositories.UserRepository;
import com.Younes43.GestionRessourcesHumains.Services.DemandeSanctionServices.Utilities.Utilities;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DemandeEscalationService {
    private final Utilities utilities;
    private final UserRepository userRepository;

    private final RapportTeamLeaderRepository rapportTeamLeaderRepository;
    private final RapportSuperviseurRepository rapportSuperviseurRepository;


    @Scheduled(cron = "0 */10 * * * *")
    public void escalateDemande() throws MessagingException, GeneralSecurityException, IOException {
        List<RAPPORT_SUPERVISEUR> rapportSuperviseurs=rapportSuperviseurRepository.findAll();
        List<RAPPORT_TEAM_LEADER> rapportTeamLeaders=rapportTeamLeaderRepository.findAll();
        for (RAPPORT_SUPERVISEUR rapportSuperviseur:rapportSuperviseurs) {
            if(!rapportSuperviseur.isProcessedByManager() && !rapportSuperviseur.isEscalatedToRh()){
                utilities.notifierRh(rapportSuperviseur);
                rapportSuperviseur.setEscalatedToRh(true);
                rapportSuperviseurRepository.save(rapportSuperviseur);
            }

        }

        for (RAPPORT_TEAM_LEADER rapportTeamLeader:rapportTeamLeaders ) {
            if(!rapportTeamLeader.isProcessedBySuperviseur() && !rapportTeamLeader.isEscalatedToRh()){
                utilities.notifierRh(rapportTeamLeader);
                rapportTeamLeader.setEscalatedToRh(true);
                rapportTeamLeaderRepository.save(rapportTeamLeader);
            }

        }


    }
}
