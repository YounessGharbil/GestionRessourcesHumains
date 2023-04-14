package com.Younes43.GestionRessourcesHumains.Controllers.DemandeDeSanctionController;

import com.Younes43.GestionRessourcesHumains.Entities.Demande_Sanction.DemandeDeSanction;
import com.Younes43.GestionRessourcesHumains.Entities.Demande_Sanction.RAPPORT_MANAGER;
import com.Younes43.GestionRessourcesHumains.Entities.Requests.CreateRapportManagerRequest;
import com.Younes43.GestionRessourcesHumains.Entities.Salarie;
import com.Younes43.GestionRessourcesHumains.IControllers.IRapportManagerController;
import com.Younes43.GestionRessourcesHumains.IServices.IDemandeDeSanctionService;
import com.Younes43.GestionRessourcesHumains.IServices.IRapportManagerService;
import com.Younes43.GestionRessourcesHumains.Services.DemandeSanctionServices.Utilities.Utilities;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Date;
import java.util.HashMap;

@RestController
@RequestMapping("/demandeDeSanction/rapportManager")
@RequiredArgsConstructor
public class RapportManagerController implements IRapportManagerController {
    private final IRapportManagerService rapportManagerService;
    private final IDemandeDeSanctionService demandeDeSanctionService;
    private final Utilities utilities;

    @PostMapping("/create")
    @Override
    public ResponseEntity<String> createRapportManager(HttpServletRequest request,
                                                       @RequestBody CreateRapportManagerRequest createRapportManagerRequest) throws MessagingException, GeneralSecurityException, IOException {
        HashMap<String,String> headers= Utilities.extractHeaders(request);
        DemandeDeSanction demandeDeSanction=demandeDeSanctionService.getDemandeDeSanction(createRapportManagerRequest.getDemandeId());
        Salarie salarie=demandeDeSanction.getSalarie();
        if(utilities.isDirect(salarie)){
            return new ResponseEntity<>("you dont have authority to create rapport manager for this employee"
                    ,HttpStatus.BAD_REQUEST);
        }

        RAPPORT_MANAGER rapportManager=RAPPORT_MANAGER.builder()
                .demandeDeSanction(demandeDeSanction)
                .userMatricule(headers.get("matricule"))
                .avisManager(createRapportManagerRequest.getAvisManager())
                .isValidated(createRapportManagerRequest.isValid())
                .date(new Date().toString())
                .sanctionDemandé(createRapportManagerRequest.getSanctionDemandé())
                .build();

        if(rapportManagerService.createRapportManager(rapportManager,headers)!=null){
            return new ResponseEntity<>("RAPPORT MANAGER CREATED", HttpStatus.CREATED);
        }
        return new ResponseEntity<>("RAPPORT MANAGER NOT CREATED", HttpStatus.BAD_REQUEST);
    }

}
