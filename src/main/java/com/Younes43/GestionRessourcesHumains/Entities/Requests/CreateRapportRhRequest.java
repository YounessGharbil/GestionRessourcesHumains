package com.Younes43.GestionRessourcesHumains.Entities.Requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateRapportRhRequest {
    private Long demandeId;
    private int nbrEnfants;
    private int age;
    private boolean valid;
}
