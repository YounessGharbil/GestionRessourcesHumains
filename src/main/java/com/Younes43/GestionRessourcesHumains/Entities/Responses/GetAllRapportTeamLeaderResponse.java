package com.Younes43.GestionRessourcesHumains.Entities.Responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetAllRapportTeamLeaderResponse {
    private Long id; 
    private Long demande_de_sanction_id;
    private String date; 
    private String date_commis; 
    private boolean escalated_to_rh;
    private String fonction; 
    private boolean  is_validated; 
    private String la_faut; 
    private boolean  processed_by_superviseur; 
    private String salarie_matricule; 
    private String section; 
    private String temoin; 
    private String user_matricule;    
}
