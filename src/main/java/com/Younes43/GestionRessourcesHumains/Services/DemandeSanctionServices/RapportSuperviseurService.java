package com.Younes43.GestionRessourcesHumains.Services.DemandeSanctionServices;
import java.util.List;

import com.Younes43.GestionRessourcesHumains.Entities.Demande_Sanction.DemandeDeSanction;
import com.Younes43.GestionRessourcesHumains.Entities.Demande_Sanction.RAPPORT_SUPERVISEUR;
import com.Younes43.GestionRessourcesHumains.Entities.Demande_Sanction.RAPPORT_TEAM_LEADER;
import com.Younes43.GestionRessourcesHumains.IServices.IRapportSuperviseurService;
import com.Younes43.GestionRessourcesHumains.Repositories.DemandeSanctionRepositories.DemandeDeSanctionRepository;
import com.Younes43.GestionRessourcesHumains.Repositories.DemandeSanctionRepositories.RapportSuperviseurRepository;
import com.Younes43.GestionRessourcesHumains.Repositories.DemandeSanctionRepositories.RapportTeamLeaderRepository;
import com.Younes43.GestionRessourcesHumains.Services.DemandeSanctionServices.Utilities.Utilities;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
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
    private final DemandeDeSanctionRepository demandeDeSanctionRepository;
    private final Utilities utilities;

    @Transactional
    @Override
    public RAPPORT_SUPERVISEUR createRapportSuperviseur( RAPPORT_SUPERVISEUR rapportSuperviseur,
                                                        HashMap<String,String> headers)
                                                        throws MessagingException, GeneralSecurityException, IOException {
        var savedRapport_superviseur=rapportSuperviseurRepository.findByDemandeDeSanction(rapportSuperviseur.getDemandeDeSanction());
        RAPPORT_TEAM_LEADER rapportTeamLeader=rapportTeamLeaderRepository.findByDemandeDeSanction(rapportSuperviseur.getDemandeDeSanction()).get();

        if (!savedRapport_superviseur.isPresent() && !rapportTeamLeader.isEscalatedToRh()) {
            utilities.sendMailToSuperior(rapportSuperviseur, headers);
            DemandeDeSanction demande=demandeDeSanctionRepository
            .findById(rapportSuperviseur.getDemandeDeSanction().getId()).get();
            RAPPORT_SUPERVISEUR rapportsuperviseur=rapportSuperviseurRepository.save(rapportSuperviseur);
            rapportTeamLeader.setProcessedBySuperviseur(true);
            demande.setRapportSuperviseur(rapportSuperviseur);
            rapportTeamLeaderRepository.save(rapportTeamLeader);
            return rapportsuperviseur;
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

    public List<RAPPORT_SUPERVISEUR> getAllRapportSuperviseur(){
        return rapportSuperviseurRepository.findAll();
    }
    @Override
    public List<RAPPORT_SUPERVISEUR> getRapportsSuperviseurNotProcessedByManager() {
        return rapportSuperviseurRepository.findAllByEscalatedToRhIsFalseAndProcessedByManagerIsFalse();
    }

}
