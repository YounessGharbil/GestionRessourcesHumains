package com.Younes43.GestionRessourcesHumains.Entities.Requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateRapportSuperviseurRequest {
    private Long demandeId;
    private String avis;
    private String sanctionDemandé;
    private boolean valid;
}
