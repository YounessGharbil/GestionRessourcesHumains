package com.Younes43.GestionRessourcesHumains.IServices;

import com.Younes43.GestionRessourcesHumains.Entities.Demande_Sanction.DemandeDeSanction;
import com.Younes43.GestionRessourcesHumains.Entities.Requests.CreateDemandeDeSanctionRequest;

public interface IDemandeDeSanctionService {
     DemandeDeSanction createDemandeDeSanction(CreateDemandeDeSanctionRequest createDemandeDeSanctionRequest);
     DemandeDeSanction getDemandeDeSanction(Long id);
    }
