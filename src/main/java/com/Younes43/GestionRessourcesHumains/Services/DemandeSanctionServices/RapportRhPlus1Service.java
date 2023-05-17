package com.Younes43.GestionRessourcesHumains.Services.DemandeSanctionServices;

import com.Younes43.GestionRessourcesHumains.Entities.Demande_Sanction.DemandeDeSanction;
import com.Younes43.GestionRessourcesHumains.Entities.Demande_Sanction.RAPPORT_RHPLUS1;
import com.Younes43.GestionRessourcesHumains.IServices.IRapportRhPlus1Service;
import com.Younes43.GestionRessourcesHumains.Repositories.DemandeSanctionRepositories.DemandeDeSanctionRepository;
import com.Younes43.GestionRessourcesHumains.Repositories.DemandeSanctionRepositories.RapportRhPlus1Repository;
import com.Younes43.GestionRessourcesHumains.Repositories.DemandeSanctionRepositories.RapportRhRepository;
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
public class RapportRhPlus1Service implements IRapportRhPlus1Service {

    private final RapportRhPlus1Repository rapportRhPlus1Repository;
    private final RapportRhRepository rapportRhRepository;
    private final DemandeDeSanctionRepository demandeDeSanctionRepository;

    private final Utilities utilities;
    @Transactional
    @Override
    public RAPPORT_RHPLUS1 createRapportRhPlus1( RAPPORT_RHPLUS1 rapportRhplus1,
                                                HashMap<String,String> headers) throws MessagingException, GeneralSecurityException, IOException {
        var savedRapport_rhplus1=rapportRhPlus1Repository.findByDemandeDeSanction(rapportRhplus1.getDemandeDeSanction());
        var savedRapport_rh=rapportRhRepository.findByDemandeDeSanction(rapportRhplus1.getDemandeDeSanction());


        if(!savedRapport_rhplus1.isPresent()){
        utilities.sendMailToSuperior(rapportRhplus1,headers);
        DemandeDeSanction demande=demandeDeSanctionRepository
        .findById(rapportRhplus1.getDemandeDeSanction().getId()).get();
        RAPPORT_RHPLUS1 raportrhplus1=rapportRhPlus1Repository.save(rapportRhplus1);
        demande.setRapportRhplus1(rapportRhplus1);
        if(savedRapport_rh.isPresent()){
            savedRapport_rh.get().setProcessedByRhPlus1(true);
        }
        return raportrhplus1;

        }
        return null;

    }
    @Override
    public RAPPORT_RHPLUS1 getRapportRhPlus1(Long id){
        RAPPORT_RHPLUS1 rapportDeLaDirection=
                                rapportRhPlus1Repository.findById(id).isPresent()?
                                rapportRhPlus1Repository.findById(id).get():null;

        return rapportDeLaDirection;
    }

    public List<RAPPORT_RHPLUS1> getAllRapportRhPlus1(){
        return rapportRhPlus1Repository.findAll();
    }
    // @Override
    // public RAPPORT_RHPLUS1 getRapportRhPlus1DemandeDeSanctionId(Long id) {
    //     RAPPORT_RHPLUS1 rapportDeLaDirection=
    //                             rapportRhPlus1Repository.findByDemandeDeSanctionId(id).isPresent()?
    //                             rapportRhPlus1Repository.findByDemandeDeSanctionId(id).get():null;
    //                             System.out.println("rapportDeLaDirection++++")  ;    
    //                             System.out.println(rapportDeLaDirection.toString())  ;

    //     return rapportDeLaDirection;
    // }

}
