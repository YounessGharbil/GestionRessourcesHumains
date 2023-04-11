package com.Younes43.GestionRessourcesHumains.Entities.Demande_Sanction;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
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
public class RAPPORT_RH implements IRapport {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotBlank
    @NotNull
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinColumn(name = "demande_de_sanction_id")
    private DemandeDeSanction demandeDeSanction;

    @NotBlank
    @NotNull
    private String  userMatricule;
    @NotBlank(message = "please enter a valid Date")
    @NotNull(message = "please enter a valid Date")
    private String dateEmbauche;
    @NotBlank
    @NotNull
    @Min(value = 17,message = "please enter a valid age")
    @Max(value = 65,message = "please enter a valid age")
    private int age;
    @NotBlank
    @NotNull
    private int nbrEnfants;
    @NotBlank(message = "please enter a valid Date")
    @NotNull(message = "please enter a valid Date")
    private String date;

    private boolean isValidated;


}
