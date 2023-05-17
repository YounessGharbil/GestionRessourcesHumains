package com.Younes43.GestionRessourcesHumains.Entities.Requests;


import com.Younes43.GestionRessourcesHumains.Entities.Enums.Role;
import com.Younes43.GestionRessourcesHumains.Entities.Enums.Site;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserRequest {
    private Long Id;
    private String email;
    private String password;
    private Role role;
    private Site site;
}
