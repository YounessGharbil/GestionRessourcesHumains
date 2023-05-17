package com.Younes43.GestionRessourcesHumains.Services.DemandeSanctionServices;

import com.Younes43.GestionRessourcesHumains.Entities.Demande_Sanction.DemandeDeSanction;
import com.Younes43.GestionRessourcesHumains.Entities.Demande_Sanction.RAPPORT_RH;
import com.Younes43.GestionRessourcesHumains.IServices.IRaportRhService;
import com.Younes43.GestionRessourcesHumains.Repositories.DemandeSanctionRepositories.DemandeDeSanctionRepository;
import com.Younes43.GestionRessourcesHumains.Repositories.DemandeSanctionRepositories.RapportManagerRepository;
import com.Younes43.GestionRessourcesHumains.Repositories.DemandeSanctionRepositories.RapportRhRepository;
import com.Younes43.GestionRessourcesHumains.Repositories.DemandeSanctionRepositories.RapportSuperviseurRepository;
import com.Younes43.GestionRessourcesHumains.Repositories.DemandeSanctionRepositories.RapportTeamLeaderRepository;
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
public class RaportRhService implements IRaportRhService {
   private final Utilities utilities;

    private final RapportRhRepository rapportRhRepository;
    private final RapportManagerRepository rapportManagerRepository;
    private final RapportTeamLeaderRepository rapportTeamLeaderRepository;
    private final RapportSuperviseurRepository rapportSuperviseurRepository;


    private final DemandeDeSanctionRepository demandeDeSanctionRepository;

   @Transactional
   @Override
   public RAPPORT_RH createRapportRh( RAPPORT_RH rapportRh,
                                     HashMap<String,String> headers) throws MessagingException, GeneralSecurityException, IOException {
      var savedRapport_rh=rapportRhRepository.findByDemandeDeSanction(rapportRh.getDemandeDeSanction());
      var savedRapport_Manager=rapportManagerRepository.findByDemandeDeSanction(rapportRh.getDemandeDeSanction());
      var savedRapport_TeamLeader=rapportTeamLeaderRepository.findByDemandeDeSanction(rapportRh.getDemandeDeSanction());
      var savedRapport_Superviseur=rapportSuperviseurRepository.findByDemandeDeSanction(rapportRh.getDemandeDeSanction());

      if(!savedRapport_rh.isPresent()){

      utilities.sendMailToSuperior(rapportRh,headers);
      DemandeDeSanction demande=demandeDeSanctionRepository
        .findById(rapportRh.getDemandeDeSanction().getId()).get();
        RAPPORT_RH rapportrh=rapportRhRepository.save(rapportRh);
        demande.setRapportRh(rapportRh);
        if(savedRapport_Manager.isPresent()){
         savedRapport_Manager.get().setProcessedByRh(true);
        }
        if(savedRapport_TeamLeader.isPresent() && savedRapport_TeamLeader.get().isEscalatedToRh()){
         savedRapport_TeamLeader.get().setProcessedBySuperviseur(true);
        }
        if(savedRapport_Superviseur.isPresent() && savedRapport_Superviseur.get().isEscalatedToRh()){
         savedRapport_Superviseur.get().setProcessedByManager(true);
        }

      return rapportrh;
      }
      return null;
   }
      @Override
      public RAPPORT_RH getRapportRh(Long id){
      RAPPORT_RH rapportRh=
                      rapportRhRepository.findById(id).isPresent()?
                      rapportRhRepository.findById(id).get():null;

      return rapportRh;
   }

   public List<RAPPORT_RH> getAllRapportRh(){
      return rapportRhRepository.findAll();
  }
   // @Override
   // public RAPPORT_RH getRapportRhByDemandeDeSanctionId(Long id) {
   //    RAPPORT_RH rapportRh=
   //                    rapportRhRepository.findByDemandeDeSanctionId(id).isPresent()?
   //                    rapportRhRepository.findByDemandeDeSanctionId(id).get():null;

   //    return rapportRh;
   // }

}
