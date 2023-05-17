package com.Younes43.GestionRessourcesHumains.Controllers.DemandeDeSanctionController;

import com.Younes43.GestionRessourcesHumains.Entities.Demande_Sanction.DemandeDeSanction;
import com.Younes43.GestionRessourcesHumains.Entities.Demande_Sanction.RAPPORT_MANAGER;
import com.Younes43.GestionRessourcesHumains.Entities.Requests.CreateRapportManagerRequest;
import com.Younes43.GestionRessourcesHumains.Entities.Responses.GetAllRapportManagerResponse;
import com.Younes43.GestionRessourcesHumains.Entities.Salarie;
import com.Younes43.GestionRessourcesHumains.IControllers.IRapportManagerController;
import com.Younes43.GestionRessourcesHumains.IServices.IDemandeDeSanctionService;
import com.Younes43.GestionRessourcesHumains.IServices.IRapportManagerService;
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
        if(salarie.getDirect().equalsIgnoreCase("TRUE")){
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

    @GetMapping("/getAll")
    public ResponseEntity<List<GetAllRapportManagerResponse>> getAllRapportManager() {

        List<GetAllRapportManagerResponse> AllRapportManager=new ArrayList<>();
        for(RAPPORT_MANAGER rapport:rapportManagerService.getAllRapportManager()){
            AllRapportManager.add(
                GetAllRapportManagerResponse.builder()
                .id(rapport.getId())
                .demande_de_sanction_id(rapport.getDemandeDeSanction().getId())
                .date(rapport.getDate())
                .is_validated(rapport.isValidated())
                .user_matricule(rapport.getUserMatricule())
                .avis_manager(rapport.getAvisManager())
                .sanction_demande(rapport.getSanctionDemandé())
                .is_processed_by_rh(rapport.isProcessedByRh())
                .build()
                ) ;
        }
        
        
        return new ResponseEntity<>(AllRapportManager,HttpStatus.OK);

    }

    @GetMapping("/get/{id}")
    public ResponseEntity<GetAllRapportManagerResponse> getRapportManager(@PathVariable Long id) {
        RAPPORT_MANAGER RapportManager=rapportManagerService.getRapportManager(id);
        if(RapportManager==null){
            return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
        }
        GetAllRapportManagerResponse response=GetAllRapportManagerResponse.builder()
        .id(RapportManager.getId())
        .demande_de_sanction_id(RapportManager.getDemandeDeSanction().getId())
        .user_matricule(RapportManager.getUserMatricule())
        .avis_manager(RapportManager.getAvisManager())
        .sanction_demande(RapportManager.getSanctionDemandé())
        .date(RapportManager.getDate())
        .is_validated(RapportManager.isValidated())
        .build();
        return new ResponseEntity<>(response,HttpStatus.OK);
    }
    // @GetMapping("/getByDemandeId/{id}")
    // @Override
    // public ResponseEntity<GetAllRapportManagerResponse> getRapportManagerByDemandeDeSanctionId(@PathVariable Long id) {
    //     RAPPORT_MANAGER RapportManager=rapportManagerService.getRapportManagerByDemandeDeSanctionId(id);
    //     if(RapportManager==null){
    //         return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
    //     }
    //     GetAllRapportManagerResponse response=GetAllRapportManagerResponse.builder()
    //     .id(RapportManager.getId())
    //     .demande_de_sanction_id(RapportManager.getDemandeDeSanction().getId())
    //     .user_matricule(RapportManager.getUserMatricule())
    //     .avis_manager(RapportManager.getAvisManager())
    //     .sanction_demande(RapportManager.getSanctionDemandé())
    //     .date(RapportManager.getDate())
    //     .is_validated(RapportManager.isValidated())
    //     .build();
    //     return new ResponseEntity<>(response,HttpStatus.OK);
    // }
}
