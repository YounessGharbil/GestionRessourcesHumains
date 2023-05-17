package com.Younes43.GestionRessourcesHumains.IControllers;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.Younes43.GestionRessourcesHumains.Entities.Demande_Sanction.DemandeDeSanction;
import com.Younes43.GestionRessourcesHumains.Entities.Responses.GetAllDemnadeDeSanctionResponse;

public interface IDemandeDeSanctionController {
    ResponseEntity<List<GetAllDemnadeDeSanctionResponse>> getAllDemandes();
    ResponseEntity<GetAllDemnadeDeSanctionResponse> getDemande(Long id);

}
