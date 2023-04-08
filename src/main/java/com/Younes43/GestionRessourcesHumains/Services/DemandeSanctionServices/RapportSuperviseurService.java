package com.Younes43.GestionRessourcesHumains.Services.DemandeSanctionServices;


import com.Younes43.GestionRessourcesHumains.Entities.Demande_Sanction.RAPPORT_SUPERVISEUR;
import com.Younes43.GestionRessourcesHumains.Entities.Demande_Sanction.RAPPORT_TEAM_LEADER;
import com.Younes43.GestionRessourcesHumains.IServices.IRapportSuperviseurService;
import com.Younes43.GestionRessourcesHumains.Repositories.DemandeSanctionRepositories.RapportSuperviseurRepository;
import com.Younes43.GestionRessourcesHumains.Repositories.DemandeSanctionRepositories.RapportTeamLeaderRepository;
import com.Younes43.GestionRessourcesHumains.Services.DemandeSanctionServices.Utilities.Utilities;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class RapportSuperviseurService implements IRapportSuperviseurService {
    private final RapportSuperviseurRepository rapportSuperviseurRepository;
    private final RapportTeamLeaderRepository rapportTeamLeaderRepository;
    private final Utilities utilities;

    @Transactional
    @Override
    public RAPPORT_SUPERVISEUR createRapportSuperviseur(RAPPORT_SUPERVISEUR rapportSuperviseur,
                                                        HashMap<String,String> headers)
                                                        throws MessagingException, GeneralSecurityException, IOException {
        if (!rapportSuperviseur.isEscalatedToRh()) {
            utilities.sendMailToSuperior(rapportSuperviseur, headers);
            RAPPORT_TEAM_LEADER rapportTeamLeader = rapportSuperviseur.getDemandeDeSanction().getRapportTeamLeader();
            rapportTeamLeader.setProcessedBySuperviseur(true);
            rapportTeamLeaderRepository.save(rapportTeamLeader);
            return rapportSuperviseurRepository.save(rapportSuperviseur);
        }
        return null;
    }
    @Override
    public RAPPORT_SUPERVISEUR getRapportSuperviseur(Long id){
        RAPPORT_SUPERVISEUR rapportDuShiftLeader=
                        rapportSuperviseurRepository.findById(id).isPresent()?
                        rapportSuperviseurRepository.findById(id).get():null;

        return rapportDuShiftLeader;
    }


}
