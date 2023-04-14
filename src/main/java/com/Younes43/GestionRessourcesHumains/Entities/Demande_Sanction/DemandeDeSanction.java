package com.Younes43.GestionRessourcesHumains.Entities.Demande_Sanction;

import com.Younes43.GestionRessourcesHumains.Entities.ApplicationUser;
import com.Younes43.GestionRessourcesHumains.Entities.Enums.DemandeStatus;
import com.Younes43.GestionRessourcesHumains.Entities.Salarie;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.annotation.Nullable;

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
    @OneToOne(mappedBy = "demandeDeSanction", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private RAPPORT_TEAM_LEADER rapportTeamLeader;
    @OneToOne(mappedBy = "demandeDeSanction", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private RAPPORT_SUPERVISEUR rapportSuperviseur;
    @OneToOne(mappedBy = "demandeDeSanction", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private RAPPORT_MANAGER rapportManager;
    @OneToOne(mappedBy = "demandeDeSanction", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private RAPPORT_RH rapportRh;
    @OneToOne(mappedBy = "demandeDeSanction", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private RAPPORT_RHPLUS1 rapportRhplus1;

    private boolean teamLeaderValidation;
    private boolean superviseurValidation;
    private boolean managerValidation;
    private boolean rhValidation;
    private boolean rhPlus1Validation;
    private String demandeStatus;
    private String niveauDeTraitement;
}
