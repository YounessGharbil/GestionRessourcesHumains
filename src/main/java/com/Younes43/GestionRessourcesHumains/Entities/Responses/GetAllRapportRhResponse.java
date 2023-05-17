package com.Younes43.GestionRessourcesHumains.Entities.Responses;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetAllRapportRhResponse {

    private Long id;
   
    private Long demande_de_sanction_id;

    private String  user_matricule;

    private String date_embauche;

    private int age;

    private int nbr_enfants;

    private String date;

    private boolean is_validated;

    private boolean is_processed_by_rhplus1;


    
}
