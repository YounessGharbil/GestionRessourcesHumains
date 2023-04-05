package com.Younes43.GestionRessourcesHumains.Controllers.DemandeDeSanctionController;

import com.Younes43.GestionRessourcesHumains.Entities.Demande_Sanction.DemandeDeSanction;
import com.Younes43.GestionRessourcesHumains.Entities.Demande_Sanction.RAPPORT_SUPERVISEUR;
import com.Younes43.GestionRessourcesHumains.Entities.Requests.CreateRapportSuperviseurRequest;
import com.Younes43.GestionRessourcesHumains.IControllers.IRapportSuperviseurController;
import com.Younes43.GestionRessourcesHumains.IServices.IDemandeDeSanctionService;
import com.Younes43.GestionRessourcesHumains.IServices.IRapportSuperviseurService;
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
@RequestMapping("/demandeDeSanction/rapportSuperviseur")
@RequiredArgsConstructor
public class RapportSuperviseurController implements IRapportSuperviseurController {
    private final IRapportSuperviseurService rapportSuperviseurService;
    private final IDemandeDeSanctionService demandeDeSanctionService;
    @PostMapping("/create")
    @Override
    public ResponseEntity<String> createRapportSuperviseur(HttpServletRequest request,
                                                           @RequestBody CreateRapportSuperviseurRequest createRapportSuperviseurRequest)
                                            throws MessagingException, GeneralSecurityException, IOException {
        HashMap<String,String> headers= Utilities.extractHeaders(request);

        DemandeDeSanction demandeDeSanction=demandeDeSanctionService.getDemandeDeSanction(createRapportSuperviseurRequest.getDemandeId());

        RAPPORT_SUPERVISEUR rapportSuperviseur=RAPPORT_SUPERVISEUR.builder()
                .demandeDeSanction(demandeDeSanction)
                .date(new Date().toString())
                .userMatricule(headers.get("matricule"))
                .avis(createRapportSuperviseurRequest.getAvis())
                .sanctionDemandé(createRapportSuperviseurRequest.getSanctionDemandé())
                .isValidated(createRapportSuperviseurRequest.isValid())
                .build();


        if( rapportSuperviseurService.createRapportSuperviseur(rapportSuperviseur,headers)!=null){
            return new ResponseEntity<>("RAPPORT SUPERVISEUR CREATED", HttpStatus.CREATED);
        }
        return new ResponseEntity<>("RAPPORT SUPERVISEUR NOT CREATED", HttpStatus.BAD_REQUEST);
    }
}
