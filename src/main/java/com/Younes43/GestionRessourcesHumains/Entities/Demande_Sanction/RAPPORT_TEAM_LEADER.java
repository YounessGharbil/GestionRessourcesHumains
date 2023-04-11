package com.Younes43.GestionRessourcesHumains.Entities.Demande_Sanction;

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
public class RAPPORT_TEAM_LEADER implements IRapport{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotBlank(message = "please enter a valid demande_de_sanction_id ")
    @NotNull(message = "please enter a valid demande_de_sanction_id")
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinColumn(name = "demande_de_sanction_id")
    private DemandeDeSanction demandeDeSanction;
    @NotBlank(message = "please enter a valid userMatricule")
    @NotNull(message = "please enter a valid userMatricule")
    private String  userMatricule;
    @NotBlank(message = "please enter a valid salariéMatricule")
    @NotNull(message = "please enter a valid salariéMatricule")
    private String  salariéMatricule;
    @NotBlank
    @NotNull
    private String  fonction;
    @NotBlank
    @NotNull
    private String section;
    @NotBlank(message = "please enter a valid Date")
    @NotNull(message = "please enter a valid Date")
    private String dateCommis;
    @NotBlank
    @NotNull
    private String laFaute;
    @NotBlank
    @NotNull
    private String temoin;
    @NotBlank(message = "please enter a valid Date")
    @NotNull(message = "please enter a valid Date")
    private String date;

    private boolean isValidated;

    private boolean processedBySuperviseur;

    private boolean escalatedToRh;



}

