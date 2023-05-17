package com.Younes43.GestionRessourcesHumains.Controllers.DemandeDeSanctionController;

import com.Younes43.GestionRessourcesHumains.Entities.Demande_Sanction.DemandeDeSanction;
import com.Younes43.GestionRessourcesHumains.Entities.Demande_Sanction.RAPPORT_RH;
import com.Younes43.GestionRessourcesHumains.Entities.Requests.CreateRapportRhRequest;
import com.Younes43.GestionRessourcesHumains.Entities.Responses.GetAllRapportRhResponse;
import com.Younes43.GestionRessourcesHumains.IControllers.IRapportRhController;
import com.Younes43.GestionRessourcesHumains.IServices.IDemandeDeSanctionService;
import com.Younes43.GestionRessourcesHumains.IServices.IRaportRhService;
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
@RequestMapping("/demandeDeSanction/rapportRh")
@RequiredArgsConstructor
public class RapportRhController implements IRapportRhController {
    private final IRaportRhService raportRhService;
    private final IDemandeDeSanctionService demandeDeSanctionService;

    @PostMapping("/create")
    @Override
    public ResponseEntity<String> createRapportRh(HttpServletRequest request,
                                                  @RequestBody CreateRapportRhRequest createRapportRhRequest) throws MessagingException, GeneralSecurityException, IOException {

        HashMap<String,String> headers= Utilities.extractHeaders(request);

        DemandeDeSanction demandeDeSanction=demandeDeSanctionService.getDemandeDeSanction(createRapportRhRequest.getDemandeId());

        RAPPORT_RH rapportRh=RAPPORT_RH.builder()
                .demandeDeSanction(demandeDeSanction)
                .nbrEnfants(createRapportRhRequest.getNbrEnfants())
                .age(createRapportRhRequest.getAge())
                .isValidated(createRapportRhRequest.isValid())
                .userMatricule(headers.get("matricule"))
                .dateEmbauche(demandeDeSanction.getSalarie().getDate_dembauche())
                .date(new Date().toString())
                .build();

        if(raportRhService.createRapportRh(rapportRh,headers)!=null){
            return new ResponseEntity<>("RAPPORT RH CREATED", HttpStatus.CREATED);
        }
        return new ResponseEntity<>("RAPPORT RH NOT CREATED", HttpStatus.BAD_REQUEST);

    }

    @GetMapping("/getAll")
    public ResponseEntity<List<GetAllRapportRhResponse>> getAllRapportRh() {

        List<GetAllRapportRhResponse> AllRapportRh=new ArrayList<>();
        for(RAPPORT_RH rapport:raportRhService.getAllRapportRh()){
            AllRapportRh.add(
                GetAllRapportRhResponse.builder()
                .id(rapport.getId())
                .date(rapport.getDate())
                .is_validated(rapport.isValidated())
                .user_matricule(rapport.getUserMatricule())
                .demande_de_sanction_id(rapport.getDemandeDeSanction().getId())
                .nbr_enfants(rapport.getNbrEnfants())
                .age(rapport.getAge())
                .date_embauche(rapport.getDateEmbauche())
                .build()
                ) ;
        }
        
        
        return new ResponseEntity<>(AllRapportRh,HttpStatus.OK);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<GetAllRapportRhResponse> getRapportRh(@PathVariable Long id) {
        RAPPORT_RH RapportRh=raportRhService.getRapportRh(id);
        if(RapportRh==null){
            return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
        }
        GetAllRapportRhResponse response=GetAllRapportRhResponse.builder()
        .id(RapportRh.getId())
        .demande_de_sanction_id(RapportRh.getDemandeDeSanction().getId())
        .user_matricule(RapportRh.getUserMatricule())
        .date_embauche(RapportRh.getDateEmbauche())
        .age(RapportRh.getAge())
        .nbr_enfants(RapportRh.getNbrEnfants())
        .date(RapportRh.getDate())
        .is_validated(RapportRh.isValidated())
        .is_processed_by_rhplus1(RapportRh.isProcessedByRhPlus1())
        .build();
        return new ResponseEntity<>(response,HttpStatus.OK);
    }
    // @GetMapping("/getByDemandeId/{id}")
    // @Override
    // public ResponseEntity<GetAllRapportRhResponse> getRapportRhByDemandeDeSanctionId(@PathVariable Long id) {
    //     RAPPORT_RH RapportRh=raportRhService.getRapportRhByDemandeDeSanctionId(id);
    //     if(RapportRh==null){
    //         return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
    //     }
    //     GetAllRapportRhResponse response=GetAllRapportRhResponse.builder()
    //     .id(RapportRh.getId())
    //     .demande_de_sanction_id(RapportRh.getDemandeDeSanction().getId())
    //     .user_matricule(RapportRh.getUserMatricule())
    //     .date_embauche(RapportRh.getDateEmbauche())
    //     .age(RapportRh.getAge())
    //     .nbr_enfants(RapportRh.getNbrEnfants())
    //     .date(RapportRh.getDate())
    //     .is_validated(RapportRh.isValidated())
    //     .build();
    //     return new ResponseEntity<>(response,HttpStatus.OK);   
    // }
}
