package com.Younes43.GestionRessourcesHumains.Services.DemandeSanctionServices;

import com.Younes43.GestionRessourcesHumains.Entities.ApplicationUser;
import com.Younes43.GestionRessourcesHumains.Entities.Demande_Sanction.RAPPORT_SUPERVISEUR;
import com.Younes43.GestionRessourcesHumains.Entities.Demande_Sanction.RAPPORT_TEAM_LEADER;
import com.Younes43.GestionRessourcesHumains.IServices.IRapportTeamLeaderService;
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

@Service
@RequiredArgsConstructor
public class RapportTeamLeaderService implements IRapportTeamLeaderService {
    private final Utilities utilities;
    private final  GmailService gmailService;
    private final RapportTeamLeaderRepository rapportTeamLeaderRepository;



    @Transactional
    @Override
    public RAPPORT_TEAM_LEADER createRapportTeamLeader( RAPPORT_TEAM_LEADER rapportTeamLeader,
                                                       HashMap<String,String> headers)
                            throws MessagingException, GeneralSecurityException, IOException {
        var savedTeamLeader=rapportTeamLeaderRepository.findByDemandeDeSanction(rapportTeamLeader.getDemandeDeSanction());

        if( !savedTeamLeader.isPresent()) {
            utilities.validateDemande(rapportTeamLeader, headers);
            ApplicationUser superior = utilities.getSuperior(rapportTeamLeader, headers);
            String validationMSG = Utilities.getValidationMSG(rapportTeamLeader.isValidated());
            gmailService.sendMail(superior.getEmail(), "demande created",
                    "demande created " + validationMSG
                            + " by " + rapportTeamLeader.getUserMatricule() + " with id="
                            + rapportTeamLeader.getDemandeDeSanction().getId());

            return rapportTeamLeaderRepository.save(rapportTeamLeader);
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

}
