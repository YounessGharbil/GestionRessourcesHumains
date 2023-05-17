package com.Younes43.GestionRessourcesHumains.Entities.Requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateRapportManagerRequest {
    private Long demandeId;
    private String avisManager;
    private String sanctionDemandé;
    private boolean valid;

}
