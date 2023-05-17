package com.Younes43.GestionRessourcesHumains.Controllers.DemandeDeSanctionController;

import com.Younes43.GestionRessourcesHumains.Entities.Demande_Sanction.DemandeDeSanction;
import com.Younes43.GestionRessourcesHumains.Entities.Demande_Sanction.RAPPORT_RHPLUS1;
import com.Younes43.GestionRessourcesHumains.Entities.Requests.CreateRapportRhPlus1Request;
import com.Younes43.GestionRessourcesHumains.Entities.Responses.GetAllRapportRhPlus1Response;
import com.Younes43.GestionRessourcesHumains.IControllers.IRapportRhPlus1Controller;
import com.Younes43.GestionRessourcesHumains.IServices.IDemandeDeSanctionService;
import com.Younes43.GestionRessourcesHumains.IServices.IRapportRhPlus1Service;
import com.Younes43.GestionRessourcesHumains.Services.DemandeSanctionServices.Utilities.Utilities;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;


import javax.mail.MessagingException;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
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

    @GetMapping("/getAll")
    public ResponseEntity<List<GetAllRapportRhPlus1Response>> getAllRapportRhPlus1() {

        List<GetAllRapportRhPlus1Response> AllRapportRhPlus1=new ArrayList<>();
        for(RAPPORT_RHPLUS1 rapport:rapportRhPlus1Service.getAllRapportRhPlus1()){
            AllRapportRhPlus1.add(
                GetAllRapportRhPlus1Response.builder()
                .id(rapport.getId())
                .date(rapport.getDate())
                .is_validated(rapport.isValidated())
                .user_matricule(rapport.getUserMatricule())
                .demande_de_sanction_id(rapport.getDemandeDeSanction().getId())
                .commentaire(rapport.getCommentaire())
                .decision_final(rapport.getDecisionFinal())
                .build()
                ) ;
        }
        
        
        return new ResponseEntity<>(AllRapportRhPlus1,HttpStatus.OK);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<GetAllRapportRhPlus1Response> getRapportRhPlus1(@PathVariable Long id) {
        RAPPORT_RHPLUS1 RapportRhPlus1=rapportRhPlus1Service.getRapportRhPlus1(id);
        if(RapportRhPlus1==null){
            return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
        }
        GetAllRapportRhPlus1Response response=GetAllRapportRhPlus1Response.builder()
        .id(RapportRhPlus1.getId())
        .demande_de_sanction_id(RapportRhPlus1.getDemandeDeSanction().getId())
        .user_matricule(RapportRhPlus1.getUserMatricule())
        .commentaire(RapportRhPlus1.getCommentaire())
        .decision_final(RapportRhPlus1.getDecisionFinal())
        .date(RapportRhPlus1.getDate())
        .is_validated(RapportRhPlus1.isValidated())
        .build();
        return new ResponseEntity<>(response,HttpStatus.OK);
    }
    // @GetMapping("/getByDemandeId/{id}")
    // @Override
    // public ResponseEntity<GetAllRapportRhPlus1Response> getRapportRhPlus1ByDemandeDeSanctionId(@PathVariable Long id) {
    //     RAPPORT_RHPLUS1 RapportRhPlus1=rapportRhPlus1Service.getRapportRhPlus1DemandeDeSanctionId(id);
    //     if(RapportRhPlus1==null){
    //         return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
    //     }
    //     GetAllRapportRhPlus1Response response=GetAllRapportRhPlus1Response.builder()
    //     .id(RapportRhPlus1.getId())
    //     .demande_de_sanction_id(RapportRhPlus1.getDemandeDeSanction().getId())
    //     .user_matricule(RapportRhPlus1.getUserMatricule())
    //     .commentaire(RapportRhPlus1.getCommentaire())
    //     .decision_final(RapportRhPlus1.getDecisionFinal())
    //     .date(RapportRhPlus1.getDate())
    //     .is_validated(RapportRhPlus1.isValidated())
    //     .build();
    //     return new ResponseEntity<>(response,HttpStatus.OK);
    // }
}
