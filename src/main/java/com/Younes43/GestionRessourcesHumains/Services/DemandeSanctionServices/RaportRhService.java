package com.Younes43.GestionRessourcesHumains.Services.DemandeSanctionServices;

import com.Younes43.GestionRessourcesHumains.Entities.Demande_Sanction.RAPPORT_RH;
import com.Younes43.GestionRessourcesHumains.IServices.IRaportRhService;
import com.Younes43.GestionRessourcesHumains.Repositories.DemandeSanctionRepositories.RapportRhRepository;
import com.Younes43.GestionRessourcesHumains.Services.DemandeSanctionServices.Utilities.Utilities;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class RaportRhService implements IRaportRhService {
   private final Utilities utilities;

    private final RapportRhRepository rapportRhRepository;
   @Transactional
   @Override
   public RAPPORT_RH createRapportRh(RAPPORT_RH rapportRh,
                                     HashMap<String,String> headers) throws MessagingException, GeneralSecurityException, IOException {

      utilities.sendMailToSuperior(rapportRh,headers);

      return rapportRhRepository.save(rapportRh);
   }
      @Override
      public RAPPORT_RH getRapportRh(Long id){
      RAPPORT_RH rapportRh=
                      rapportRhRepository.findById(id).isPresent()?
                      rapportRhRepository.findById(id).get():null;

      return rapportRh;
   }

}
