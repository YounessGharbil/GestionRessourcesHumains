package com.Younes43.GestionRessourcesHumains.Entities.Responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponse {
    private String token;
    private String username;
    private String userRole;
    private String matricule;
    private String department;
    private String site;
}
