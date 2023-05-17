package com.Younes43.GestionRessourcesHumains.Services.DemandeSanctionServices;

import com.Younes43.GestionRessourcesHumains.Entities.ApplicationUser;
import com.Younes43.GestionRessourcesHumains.Entities.Demande_Sanction.DemandeDeSanction;
import com.Younes43.GestionRessourcesHumains.Entities.Demande_Sanction.RAPPORT_SUPERVISEUR;
import com.Younes43.GestionRessourcesHumains.Entities.Demande_Sanction.RAPPORT_TEAM_LEADER;
import com.Younes43.GestionRessourcesHumains.IServices.IRapportTeamLeaderService;
import com.Younes43.GestionRessourcesHumains.Repositories.DemandeSanctionRepositories.DemandeDeSanctionRepository;
import com.Younes43.GestionRessourcesHumains.Repositories.DemandeSanctionRepositories.RapportTeamLeaderRepository;
import com.Younes43.GestionRessourcesHumains.Services.DemandeSanctionServices.Utilities.Utilities;
import com.Younes43.GestionRessourcesHumains.Services.GmailService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.HashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RapportTeamLeaderService implements IRapportTeamLeaderService {
    private final Utilities utilities;
    private final  GmailService gmailService;
    private final RapportTeamLeaderRepository rapportTeamLeaderRepository;
    private final DemandeDeSanctionRepository demandeDeSanctionRepository;




    @Transactional
    @Override
    public RAPPORT_TEAM_LEADER createRapportTeamLeader( RAPPORT_TEAM_LEADER rapportTeamLeader,
                                                       HashMap<String,String> headers)
                            throws MessagingException, GeneralSecurityException, IOException {
        var savedTeamLeader=rapportTeamLeaderRepository.findByDemandeDeSanction(rapportTeamLeader.getDemandeDeSanction());
      
        if( !savedTeamLeader.isPresent()) {
            utilities.sendMailToSuperior(rapportTeamLeader, headers);
            DemandeDeSanction demande=demandeDeSanctionRepository
            .findById(rapportTeamLeader.getDemandeDeSanction().getId()).get();
            RAPPORT_TEAM_LEADER rapportteamleader=rapportTeamLeaderRepository.save(rapportTeamLeader);
            demande.setRapportTeamLeader(rapportteamleader);
            demande.setDepartement(headers.get("department"));
            demande.setSite(headers.get("site"));
            // utilities.setRapportToDemandeDeSanction(savedRapportTeamLeader, rapportTeamLeader.getDemandeDeSanction().getId());
            return rapportteamleader;
        }
        return null;
    }
    @Override
    public RAPPORT_TEAM_LEADER getRapportTeamLeader(Long id){
        RAPPORT_TEAM_LEADER rapportChefDirect=
                        rapportTeamLeaderRepository.findById(id).isPresent()?
                        rapportTeamLeaderRepository.findById(id).get():null;

        return rapportChefDirect;
    }

    public List<RAPPORT_TEAM_LEADER> getAllRapportTeamLeader(){
        return rapportTeamLeaderRepository.findAll();
    }
    @Override
    public List<RAPPORT_TEAM_LEADER> getRapportsTeamLeaderNotProcessedBySperviseur() {
        return rapportTeamLeaderRepository.findAllByEscalatedToRhIsFalseAndProcessedBySuperviseurIsFalse();
    }
    // @Override
    // public RAPPORT_TEAM_LEADER getRapportTeamLeaderByDemandeDeSanctionId(Long id) {
    //     DemandeDeSanction demande=demandeDeSanctionRepository.findById(id).get();

    //     RAPPORT_TEAM_LEADER rapportChefDirect=
    //     rapportTeamLeaderRepository.findByDemandeDeSanctionId(id).isPresent()?
    //     rapportTeamLeaderRepository.findByDemandeDeSanctionId(id).get():null;  
        
    //     return rapportChefDirect;
    // }

}
