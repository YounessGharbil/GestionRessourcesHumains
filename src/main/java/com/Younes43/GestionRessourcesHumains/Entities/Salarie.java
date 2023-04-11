package com.Younes43.GestionRessourcesHumains.Entities;

import com.Younes43.GestionRessourcesHumains.Entities.Enums.BusinessUnit;
import com.Younes43.GestionRessourcesHumains.Entities.Enums.TypeContrat;
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
    @NotBlank(message = "")
    @NotNull(message = "")
    private String matricule;

    @NotBlank(message = "")
    @NotNull(message = "")
    private String nom;
    @NotBlank
    @NotNull
    private String prenom;
    @NotBlank(message = "please enter a valid Date")
    @NotNull(message = "please enter a valid Date")
    private String date_dembauche;
    @NotBlank
    @NotNull
    private String segment;
    @NotBlank
    @NotNull
    @Enumerated(EnumType.STRING)
    private BusinessUnit bu;
    @NotBlank
    @NotNull
    private String site;
    private String code_site;
    @NotBlank
    @NotNull
    private String departement;
    @NotBlank
    @NotNull
    private String local_job_title;
    @NotBlank
    @NotNull
    private String position;
    @NotBlank
    @NotNull
    private String supervisor;
    @NotBlank
    @NotNull
    private String genre;
    @Enumerated(EnumType.STRING)
    @NotBlank
    @NotNull
    private TypeContrat type_de_contrat;
    @NotBlank
    @NotNull
    private String status;
}
