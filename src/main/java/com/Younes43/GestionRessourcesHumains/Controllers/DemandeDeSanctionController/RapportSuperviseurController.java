package com.Younes43.GestionRessourcesHumains.Controllers.DemandeDeSanctionController;

import com.Younes43.GestionRessourcesHumains.Entities.Demande_Sanction.DemandeDeSanction;
import com.Younes43.GestionRessourcesHumains.Entities.Demande_Sanction.RAPPORT_SUPERVISEUR;
import com.Younes43.GestionRessourcesHumains.Entities.Requests.CreateRapportSuperviseurRequest;
import com.Younes43.GestionRessourcesHumains.Entities.Responses.GetAllRapportSuperviseurResponse;
import com.Younes43.GestionRessourcesHumains.IControllers.IRapportSuperviseurController;
import com.Younes43.GestionRessourcesHumains.IServices.IDemandeDeSanctionService;
import com.Younes43.GestionRessourcesHumains.IServices.IRapportSuperviseurService;
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

    @GetMapping("/getAll")
    public ResponseEntity<List<GetAllRapportSuperviseurResponse>> getAllRapportSuperviseur() {
        List<GetAllRapportSuperviseurResponse> AllRapportSuperviseur=new ArrayList<>();
        for(RAPPORT_SUPERVISEUR rapport:rapportSuperviseurService.getAllRapportSuperviseur()){
            AllRapportSuperviseur.add(
                GetAllRapportSuperviseurResponse.builder()
                .id(rapport.getId())
                .demande_de_sanction_id(rapport.getDemandeDeSanction().getId())
                .avis(rapport.getAvis())
                .user_matricule(rapport.getUserMatricule())
                .date(rapport.getDate())
                .is_validated(rapport.isValidated())
                .sanction_demande(rapport.getSanctionDemandé())
                .processed_by_manager(rapport.isProcessedByManager())
                .escalated_to_rh(rapport.isEscalatedToRh())
                .build()
                ) ;
        }
        
        
        return new ResponseEntity<>(AllRapportSuperviseur,HttpStatus.OK);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<GetAllRapportSuperviseurResponse> getRapportSuperviseur(@PathVariable Long id) {
        RAPPORT_SUPERVISEUR RapportSuperviseur=rapportSuperviseurService.getRapportSuperviseur(id);
        if(RapportSuperviseur==null){
            return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
        }
        GetAllRapportSuperviseurResponse response=GetAllRapportSuperviseurResponse.builder()
        .id(RapportSuperviseur.getId())
        .demande_de_sanction_id(RapportSuperviseur.getDemandeDeSanction().getId())
        .user_matricule(RapportSuperviseur.getUserMatricule())
        .avis(RapportSuperviseur.getAvis())
        .sanction_demande(RapportSuperviseur.getSanctionDemandé())
        .date(RapportSuperviseur.getDate())
        .is_validated(RapportSuperviseur.isValidated())
        .processed_by_manager(RapportSuperviseur.isProcessedByManager())
        .escalated_to_rh(RapportSuperviseur.isEscalatedToRh())
        .build();
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @GetMapping("/getAllNotProcessed")
    @Override
    public ResponseEntity<List<GetAllRapportSuperviseurResponse>> getRapportsSuperviseurNotProcessedByManager() {
        List<GetAllRapportSuperviseurResponse> RapportsSuperviseur=new ArrayList<>();
        for(RAPPORT_SUPERVISEUR rapport:rapportSuperviseurService.getRapportsSuperviseurNotProcessedByManager()){
            RapportsSuperviseur.add(
                GetAllRapportSuperviseurResponse.builder()
                .id(rapport.getId())
                .demande_de_sanction_id(rapport.getDemandeDeSanction().getId())
                .avis(rapport.getAvis())
                .user_matricule(rapport.getUserMatricule())
                .date(rapport.getDate())
                .is_validated(rapport.isValidated())
                .sanction_demande(rapport.getSanctionDemandé())
                .processed_by_manager(rapport.isProcessedByManager())
                .escalated_to_rh(rapport.isEscalatedToRh())
                .build()
                ) ;
        }
        
        
        return new ResponseEntity<>(RapportsSuperviseur,HttpStatus.OK);
    }

}
