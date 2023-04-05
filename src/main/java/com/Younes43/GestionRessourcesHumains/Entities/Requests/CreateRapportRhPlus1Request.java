package com.Younes43.GestionRessourcesHumains.Entities.Requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateRapportRhPlus1Request {
    private Long demandeId;
    private String commentaire;
    private String decisionFinal;
    private boolean valid;
}
