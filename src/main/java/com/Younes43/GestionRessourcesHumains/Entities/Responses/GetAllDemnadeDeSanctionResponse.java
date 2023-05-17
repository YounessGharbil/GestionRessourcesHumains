package com.Younes43.GestionRessourcesHumains.Entities.Responses;
import com.Younes43.GestionRessourcesHumains.Entities.ApplicationUser;
import com.Younes43.GestionRessourcesHumains.Entities.Salarie;
import com.Younes43.GestionRessourcesHumains.Entities.Demande_Sanction.RAPPORT_MANAGER;
import com.Younes43.GestionRessourcesHumains.Entities.Demande_Sanction.RAPPORT_RH;
import com.Younes43.GestionRessourcesHumains.Entities.Demande_Sanction.RAPPORT_RHPLUS1;
import com.Younes43.GestionRessourcesHumains.Entities.Demande_Sanction.RAPPORT_SUPERVISEUR;
import com.Younes43.GestionRessourcesHumains.Entities.Demande_Sanction.RAPPORT_TEAM_LEADER;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetAllDemnadeDeSanctionResponse {

   
    private Long id;
    private Long salarie;
    private Long user;
    private Long rapportTeamLeader;
    private Long rapportSuperviseur;
    private Long rapportManager;
    private Long rapportRh;
    private Long rapportRhplus1;
    private boolean teamLeaderValidation;
    private boolean superviseurValidation;
    private boolean managerValidation;
    private boolean rhValidation;
    private boolean rhPlus1Validation;
    private String demandeStatus;
    private String niveauDeTraitement;
    private String departement;
    private String site;
    
}
