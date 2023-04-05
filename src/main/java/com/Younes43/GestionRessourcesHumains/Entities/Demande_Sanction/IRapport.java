package com.Younes43.GestionRessourcesHumains.Entities.Demande_Sanction;

public interface IRapport {
    boolean isValidated();

    DemandeDeSanction getDemandeDeSanction();

    String getUserMatricule();
}
