package com.Younes43.GestionRessourcesHumains.Services.DemandeSanctionServices;

import com.Younes43.GestionRessourcesHumains.Entities.Demande_Sanction.DemandeDeSanction;
import com.Younes43.GestionRessourcesHumains.Entities.Demande_Sanction.RAPPORT_MANAGER;
import com.Younes43.GestionRessourcesHumains.Entities.Demande_Sanction.RAPPORT_SUPERVISEUR;
import com.Younes43.GestionRessourcesHumains.IServices.IRapportManagerService;
import com.Younes43.GestionRessourcesHumains.Repositories.DemandeSanctionRepositories.DemandeDeSanctionRepository;
import com.Younes43.GestionRessourcesHumains.Repositories.DemandeSanctionRepositories.RapportManagerRepository;
import com.Younes43.GestionRessourcesHumains.Repositories.DemandeSanctionRepositories.RapportSuperviseurRepository;
import com.Younes43.GestionRessourcesHumains.Services.DemandeSanctionServices.Utilities.Utilities;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import javax.mail.MessagingException;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.HashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RapportManagerService implements IRapportManagerService {
    private final Utilities utilities;
    private final RapportManagerRepository rapportManagerRepository;
    private final RapportSuperviseurRepository rapportSuperviseurRepository ;
    private final DemandeDeSanctionRepository demandeDeSanctionRepository;


    @Transactional
    @Override
    public RAPPORT_MANAGER createRapportManager( RAPPORT_MANAGER rapportManager,
                                                HashMap<String,String> headers ) throws MessagingException, GeneralSecurityException, IOException {
        var savedRapport_manager=rapportManagerRepository.findByDemandeDeSanction(rapportManager.getDemandeDeSanction());
        RAPPORT_SUPERVISEUR rapport_superviseur=rapportSuperviseurRepository.findByDemandeDeSanction(rapportManager.getDemandeDeSanction()).get();
    if(!savedRapport_manager.isPresent() && !rapport_superviseur.isEscalatedToRh()){
        utilities.sendMailToSuperior(rapportManager,headers);
        DemandeDeSanction demande=demandeDeSanctionRepository
        .findById(rapportManager.getDemandeDeSanction().getId()).get();
        RAPPORT_MANAGER rapportmanager=rapportManagerRepository.save(rapportManager);
        demande.setRapportManager(rapportManager);
        RAPPORT_SUPERVISEUR rapportSuperviseur=rapportManager.getDemandeDeSanction().getRapportSuperviseur();
        rapportSuperviseur.setProcessedByManager(true);
        rapportSuperviseurRepository.save(rapportSuperviseur);
        return rapportmanager;
    }
    return null;
    }
    @Override
    public RAPPORT_MANAGER getRapportManager(Long id){
        RAPPORT_MANAGER rapport_manager=
                        rapportManagerRepository.findById(id).isPresent()?
                        rapportManagerRepository.findById(id).get():null;

        return rapport_manager;
    }

    public List<RAPPORT_MANAGER> getAllRapportManager(){
        return rapportManagerRepository.findAll();
    }
    // @Override
    // public RAPPORT_MANAGER getRapportManagerByDemandeDeSanctionId(Long id) {
    //     RAPPORT_MANAGER rapport_manager=
    //     rapportManagerRepository.findByDemandeDeSanctionId(id).isPresent()?
    //     rapportManagerRepository.findByDemandeDeSanctionId(id).get():null;
    //     System.out.println("rapport_manager++++")  ;    
    //     System.out.println(rapport_manager.toString())  ;
    //     return rapport_manager; 
    
    // }

}
