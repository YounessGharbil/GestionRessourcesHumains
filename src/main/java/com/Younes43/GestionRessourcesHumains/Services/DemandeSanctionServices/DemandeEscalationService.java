package com.Younes43.GestionRessourcesHumains.Services.DemandeSanctionServices;

import com.Younes43.GestionRessourcesHumains.Entities.Demande_Sanction.RAPPORT_SUPERVISEUR;
import com.Younes43.GestionRessourcesHumains.Entities.Demande_Sanction.RAPPORT_TEAM_LEADER;
import com.Younes43.GestionRessourcesHumains.Repositories.DemandeSanctionRepositories.RapportSuperviseurRepository;
import com.Younes43.GestionRessourcesHumains.Repositories.DemandeSanctionRepositories.RapportTeamLeaderRepository;
import com.Younes43.GestionRessourcesHumains.Repositories.UserRepository;
import com.Younes43.GestionRessourcesHumains.Services.DemandeSanctionServices.Utilities.Utilities;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class DemandeEscalationService {
    private final Utilities utilities;
    private final UserRepository userRepository;

    private final RapportTeamLeaderRepository rapportTeamLeaderRepository;
    private final RapportSuperviseurRepository rapportSuperviseurRepository;


    @Scheduled(cron = "0 */5 * ? * *")
    public void escalateDemande() throws MessagingException, GeneralSecurityException, IOException {
        List<RAPPORT_SUPERVISEUR> rapportSuperviseurs=rapportSuperviseurRepository.findAllByEscalatedToRhIsFalseAndProcessedByManagerIsFalse();
        List<RAPPORT_TEAM_LEADER> rapportTeamLeaders=rapportTeamLeaderRepository.findAllByEscalatedToRhIsFalseAndProcessedBySuperviseurIsFalse();
        for (RAPPORT_SUPERVISEUR rapportSuperviseur:rapportSuperviseurs) {
                utilities.notifierRh(rapportSuperviseur);
                rapportSuperviseur.setEscalatedToRh(true);
                rapportSuperviseurRepository.save(rapportSuperviseur);
                log.info("rapportSuperviseur with rapportid="+rapportSuperviseur.getId()
                        +" and demandeid="+rapportSuperviseur.getDemandeDeSanction().getId()
                        +" escalated to rh");

        }

        for (RAPPORT_TEAM_LEADER rapportTeamLeader:rapportTeamLeaders ) {
                utilities.notifierRh(rapportTeamLeader);
                rapportTeamLeader.setEscalatedToRh(true);
                rapportTeamLeaderRepository.save(rapportTeamLeader);
                log.info("rapportTeamLeader with rapportid="+rapportTeamLeader.getId()
                        +" and demandeid="+rapportTeamLeader.getDemandeDeSanction().getId()
                        +" escalated to rh");


        }


    }
}
