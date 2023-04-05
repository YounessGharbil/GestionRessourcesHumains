package com.Younes43.GestionRessourcesHumains.Entities;

import com.Younes43.GestionRessourcesHumains.Entities.Enums.BusinessUnit;
import com.Younes43.GestionRessourcesHumains.Entities.Enums.TypeContrat;
import jakarta.persistence.*;
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
    private String segment;
    @Enumerated(EnumType.STRING)
    private BusinessUnit bu;
    private String site;
    private String code_site;
    private String departement;
    private String local_job_title;
    private String position;
    private String supervisor;
    private String genre;
    @Enumerated(EnumType.STRING)
    private TypeContrat type_de_contrat;
    private String status;
}
