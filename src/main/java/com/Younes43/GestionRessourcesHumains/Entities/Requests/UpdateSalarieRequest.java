package com.Younes43.GestionRessourcesHumains.Entities.Requests;

import com.Younes43.GestionRessourcesHumains.Entities.Enums.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateSalarieRequest {
    private Long Id;
    private String nom;
    private String prenom;
    private Segment segment;

    private BusinessUnit bu;
    private Site site;
    private CodeSite code_site;
    private String departement;

    private String local_job_title;
    private String position;
    private String supervisor;
    private Genre genre;

    private TypeContrat type_de_contrat;
    private String status;
}
