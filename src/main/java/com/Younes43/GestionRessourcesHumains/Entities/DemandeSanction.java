package com.Younes43.GestionRessourcesHumains.Entities;

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
@Builder
@DynamicUpdate
@DynamicInsert
public class DemandeSanction {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String createdBy;
    private String salarieConcerne;
    private String departement;
    private String date;
    private String objet;
    private boolean validatedByTeamLeader;
    private boolean validatedBySuperviseur;
    private boolean validatedByManager;
    private boolean validatedByRH;
    private boolean validatedByRHplus1;




}
