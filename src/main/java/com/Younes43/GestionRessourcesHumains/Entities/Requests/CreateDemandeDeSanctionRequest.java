package com.Younes43.GestionRessourcesHumains.Entities.Requests;

import com.Younes43.GestionRessourcesHumains.Entities.ApplicationUser;
import com.Younes43.GestionRessourcesHumains.Entities.Enums.DemandeStatus;
import com.Younes43.GestionRessourcesHumains.Entities.Salarie;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateDemandeDeSanctionRequest {
    private ApplicationUser user;
    private Salarie salarie;
    private DemandeStatus demandeStatus;
}
