package com.Younes43.GestionRessourcesHumains.Entities.Demande_Sanction;

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
public class RAPPORT_SUPERVISEUR implements IRapport {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinColumn(name = "demande_de_sanction_id")
    private DemandeDeSanction demandeDeSanction;

    private String  userMatricule;
    private String avis;
    private String sanctionDemandé;
    private String date;
    private boolean isValidated;
    private boolean processedByManager;
    private boolean escalatedToRh;




}
