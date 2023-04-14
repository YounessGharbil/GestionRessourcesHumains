package com.Younes43.GestionRessourcesHumains.Services.DemandeSanctionServices;

import com.Younes43.GestionRessourcesHumains.Entities.Demande_Sanction.DemandeDeSanction;
import com.Younes43.GestionRessourcesHumains.Entities.Enums.NiveauDeTraitement;
import com.Younes43.GestionRessourcesHumains.Entities.Requests.CreateDemandeDeSanctionRequest;
import com.Younes43.GestionRessourcesHumains.IServices.IDemandeDeSanctionService;
import com.Younes43.GestionRessourcesHumains.Repositories.DemandeSanctionRepositories.DemandeDeSanctionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DemandeDeSanctionService implements IDemandeDeSanctionService {
     private final DemandeDeSanctionRepository demandeDeSanctionRepository;
    @Override
    public DemandeDeSanction createDemandeDeSanction(CreateDemandeDeSanctionRequest createDemandeDeSanctionRequest){

        return demandeDeSanctionRepository.save(DemandeDeSanction.builder()
                        .salarie(createDemandeDeSanctionRequest.getSalarie())
                        .demandeStatus(createDemandeDeSanctionRequest.getDemandeStatus())
                        .user(createDemandeDeSanctionRequest.getUser())
                .build());
    }

    @Override
    public DemandeDeSanction getDemandeDeSanction(Long id){
        DemandeDeSanction demandeDeSanction=
                        demandeDeSanctionRepository.findById(id).isPresent()?
                        demandeDeSanctionRepository.findById(id).get():null;

        return demandeDeSanction;
    }
}

