package com.Younes43.GestionRessourcesHumains.Entities.Responses;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetAllRapportRhPlus1Response {
   
    private Long id;

    private Long demande_de_sanction_id;

    private String  user_matricule;

    private String commentaire;

    private String decision_final;

    private String date;

    private boolean is_validated;
    
}
