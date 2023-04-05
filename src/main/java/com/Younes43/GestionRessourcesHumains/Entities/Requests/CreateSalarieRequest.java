package com.Younes43.GestionRessourcesHumains.Entities.Requests;

import com.Younes43.GestionRessourcesHumains.Entities.Enums.BusinessUnit;
import com.Younes43.GestionRessourcesHumains.Entities.Enums.TypeContrat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateSalarieRequest {

    private String nom;
    private String prenom;
    private String segment;
    private BusinessUnit bu;
    private String site;
    private String code_site;
    private String departement;

    private String local_job_title;
    private String position;
    private String supervisor;
    private String genre;

    private TypeContrat type_de_contrat;
    private String status;
}
