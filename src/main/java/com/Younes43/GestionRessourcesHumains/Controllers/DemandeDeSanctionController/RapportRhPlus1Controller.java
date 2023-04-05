package com.Younes43.GestionRessourcesHumains.Controllers.DemandeDeSanctionController;

import com.Younes43.GestionRessourcesHumains.Entities.Demande_Sanction.DemandeDeSanction;
import com.Younes43.GestionRessourcesHumains.Entities.Demande_Sanction.RAPPORT_RHPLUS1;
import com.Younes43.GestionRessourcesHumains.Entities.Requests.CreateRapportRhPlus1Request;
import com.Younes43.GestionRessourcesHumains.IControllers.IRapportRhPlus1Controller;
import com.Younes43.GestionRessourcesHumains.IServices.IDemandeDeSanctionService;
import com.Younes43.GestionRessourcesHumains.IServices.IRapportRhPlus1Service;
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
@RequestMapping("/demandeDeSanction/rapportRhPlus1")
@RequiredArgsConstructor
public class RapportRhPlus1Controller implements IRapportRhPlus1Controller {
    private final IRapportRhPlus1Service rapportRhPlus1Service;
    private final IDemandeDeSanctionService demandeDeSanctionService;

    @PostMapping("/create")
    @Override
    public ResponseEntity<String> createRapportRhPlus1(HttpServletRequest request,
                                                       @RequestBody CreateRapportRhPlus1Request createRapportRhPlus1Request) throws MessagingException, GeneralSecurityException, IOException {
        HashMap<String,String> headers= Utilities.extractHeaders(request);

        DemandeDeSanction demandeDeSanction=demandeDeSanctionService.getDemandeDeSanction(createRapportRhPlus1Request.getDemandeId());

        RAPPORT_RHPLUS1 rapportRhplus1=RAPPORT_RHPLUS1.builder()
                .demandeDeSanction(demandeDeSanction)
                .userMatricule(headers.get("matricule"))
                .date(new Date().toString())
                .isValidated(createRapportRhPlus1Request.isValid())
                .commentaire(createRapportRhPlus1Request.getCommentaire())
                .decisionFinal(createRapportRhPlus1Request.getDecisionFinal())
                .build();



        if(rapportRhPlus1Service.createRapportRhPlus1(rapportRhplus1,headers)!=null){
            return new ResponseEntity<>("RAPPORT RHPLUS1 CREATED", HttpStatus.CREATED);
        }
        return new ResponseEntity<>("RAPPORT RHPLUS1 NOT CREATED", HttpStatus.BAD_REQUEST);

    }
}
