package com.Younes43.GestionRessourcesHumains.Entities;

import com.Younes43.GestionRessourcesHumains.Entities.Enums.*;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;



@Entity
@Data
@Table
@AllArgsConstructor
@NoArgsConstructor
@DynamicUpdate
@DynamicInsert
@Builder
public class Salarie {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String matricule;

    private String nom;

    private String prenom;

    private String date_dembauche;
    @Enumerated(EnumType.STRING)
    private Segment segment;

    @Enumerated(EnumType.STRING)
    private BusinessUnit bu;
    @Enumerated(EnumType.STRING)
    private Site site;
    @Enumerated(EnumType.STRING)
    private CodeSite code_site;

    private String departement;

    private String local_job_title;

    private String position;

    private String supervisor;

    @Enumerated(EnumType.STRING)
    private Genre genre;
    @Enumerated(EnumType.STRING)
    private TypeContrat type_de_contrat;

    private String status;
}
