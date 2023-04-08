package com.Younes43.GestionRessourcesHumains.Services.DemandeSanctionServices;

import com.Younes43.GestionRessourcesHumains.Entities.Demande_Sanction.RAPPORT_MANAGER;
import com.Younes43.GestionRessourcesHumains.Entities.Demande_Sanction.RAPPORT_SUPERVISEUR;
import com.Younes43.GestionRessourcesHumains.IServices.IRapportManagerService;
import com.Younes43.GestionRessourcesHumains.Repositories.DemandeSanctionRepositories.DemandeDeSanctionRepository;
import com.Younes43.GestionRessourcesHumains.Repositories.DemandeSanctionRepositories.RapportManagerRepository;
import com.Younes43.GestionRessourcesHumains.Repositories.DemandeSanctionRepositories.RapportSuperviseurRepository;
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
public class RapportManagerService implements IRapportManagerService {
    private final Utilities utilities;
    private final RapportManagerRepository rapportManagerRepository;
    private final RapportSuperviseurRepository rapportSuperviseurRepository ;

    @Transactional
    @Override
    public RAPPORT_MANAGER createRapportManager(RAPPORT_MANAGER rapportManager,
                                                HashMap<String,String> headers ) throws MessagingException, GeneralSecurityException, IOException {
       utilities.sendMailToSuperior(rapportManager,headers);
        RAPPORT_SUPERVISEUR rapportSuperviseur=rapportManager.getDemandeDeSanction().getRapportSuperviseur();
        rapportSuperviseur.setProcessedByManager(true);
        rapportSuperviseurRepository.save(rapportSuperviseur);
        return rapportManagerRepository.save(rapportManager);
    }
    @Override
    public RAPPORT_MANAGER getRapportManager(Long id){
        RAPPORT_MANAGER rapport_manager=
                        rapportManagerRepository.findById(id).isPresent()?
                        rapportManagerRepository.findById(id).get():null;

        return rapport_manager;
    }

}
