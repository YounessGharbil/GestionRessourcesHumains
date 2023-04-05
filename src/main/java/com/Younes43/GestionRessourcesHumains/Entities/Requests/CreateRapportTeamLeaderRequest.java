package com.Younes43.GestionRessourcesHumains.Entities.Requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateRapportTeamLeaderRequest {
    private String  salariéMatricule;
    private String dateCommis;
    private String laFaute;
    private boolean valid;
}
