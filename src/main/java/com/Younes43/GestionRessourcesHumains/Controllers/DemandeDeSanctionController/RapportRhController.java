package com.Younes43.GestionRessourcesHumains.Controllers.DemandeDeSanctionController;

import com.Younes43.GestionRessourcesHumains.Entities.Demande_Sanction.DemandeDeSanction;
import com.Younes43.GestionRessourcesHumains.Entities.Demande_Sanction.RAPPORT_RH;
import com.Younes43.GestionRessourcesHumains.Entities.Requests.CreateRapportRhRequest;
import com.Younes43.GestionRessourcesHumains.IControllers.IRapportRhController;
import com.Younes43.GestionRessourcesHumains.IServices.IDemandeDeSanctionService;
import com.Younes43.GestionRessourcesHumains.IServices.IRaportRhService;
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
}
