package com.Younes43.GestionRessourcesHumains.Entities.Demande_Sanction;

import com.Younes43.GestionRessourcesHumains.Entities.ApplicationUser;

import com.Younes43.GestionRessourcesHumains.Entities.Salarie;
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
public class DemandeDeSanction {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinColumn(name = "salarie_id")
    private Salarie salarie;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinColumn(name = "user_id")
    private ApplicationUser user;
    @OneToOne( cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    @JoinColumn(name = "rapport_team_leader_id")
    private RAPPORT_TEAM_LEADER rapportTeamLeader;
    @OneToOne( cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    @JoinColumn(name = "rapport_superviseur_id",nullable = true)
    private RAPPORT_SUPERVISEUR rapportSuperviseur;
    @OneToOne( cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    @JoinColumn(name = "rapport_manager_id",nullable = true)
    private RAPPORT_MANAGER rapportManager;
    @OneToOne( cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    @JoinColumn(name = "rapport_rh_id")
    private RAPPORT_RH rapportRh;
    @OneToOne( cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    @JoinColumn(name = "rapport_rhplus1_id")
    private RAPPORT_RHPLUS1 rapportRhplus1;

    private boolean teamLeaderValidation;
    private boolean superviseurValidation;
    private boolean managerValidation;
    private boolean rhValidation;
    private boolean rhPlus1Validation;
    private String demandeStatus;
    private String niveauDeTraitement;
    private String site;
    private String departement;
    
}
