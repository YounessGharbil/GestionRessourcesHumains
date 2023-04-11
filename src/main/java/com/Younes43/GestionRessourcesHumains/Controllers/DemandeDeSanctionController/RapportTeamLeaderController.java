package com.Younes43.GestionRessourcesHumains.Controllers.DemandeDeSanctionController;

import com.Younes43.GestionRessourcesHumains.Entities.ApplicationUser;
import com.Younes43.GestionRessourcesHumains.Entities.Demande_Sanction.DemandeDeSanction;
import com.Younes43.GestionRessourcesHumains.Entities.Demande_Sanction.RAPPORT_TEAM_LEADER;
import com.Younes43.GestionRessourcesHumains.Entities.Enums.DemandeStatus;
import com.Younes43.GestionRessourcesHumains.Entities.Requests.CreateDemandeDeSanctionRequest;
import com.Younes43.GestionRessourcesHumains.Entities.Requests.CreateRapportTeamLeaderRequest;
import com.Younes43.GestionRessourcesHumains.Entities.Salarie;
import com.Younes43.GestionRessourcesHumains.IControllers.IRapportTeamLeaderController;
import com.Younes43.GestionRessourcesHumains.IServices.IDemandeDeSanctionService;
import com.Younes43.GestionRessourcesHumains.IServices.IRapportTeamLeaderService;
import com.Younes43.GestionRessourcesHumains.IServices.ISalarieService;
import com.Younes43.GestionRessourcesHumains.IServices.IUserService;
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
@RequestMapping("/demandeDeSanction/rapportTeamLeader")
@RequiredArgsConstructor
public class RapportTeamLeaderController implements IRapportTeamLeaderController {
   private final IUserService userService;

   private final IRapportTeamLeaderService rapportTeamLeaderService;

   private final IDemandeDeSanctionService demandeDeSanctionService;

   private final ISalarieService salarieService;
   @PostMapping("/create")
   @Override
   public ResponseEntity<String> createRapportTeamLeader(HttpServletRequest request,
                                                         @RequestBody CreateRapportTeamLeaderRequest createRapportTeamLeaderRequest) throws MessagingException, GeneralSecurityException, IOException {

      HashMap<String,String> headers= Utilities.extractHeaders(request);

      Salarie salarie= salarieService.getSalarieBySalarieId(createRapportTeamLeaderRequest.getSalariéMatricule());

      ApplicationUser user=userService.getUser(headers.get("matricule"));

      CreateDemandeDeSanctionRequest createDemandeDeSanctionRequest=CreateDemandeDeSanctionRequest.builder()
              .user(user)
              .demandeStatus(DemandeStatus.En_Traitement.name())
              .salarie(salarie)
              .build();

      DemandeDeSanction demandeDeSanction=demandeDeSanctionService
              .createDemandeDeSanction(createDemandeDeSanctionRequest);


      RAPPORT_TEAM_LEADER rapportTeamLeader=RAPPORT_TEAM_LEADER.builder()
              .demandeDeSanction(demandeDeSanction)
              .userMatricule(headers.get("matricule"))
              .fonction(salarie.getLocal_job_title())
              .section(salarie.getDepartement())
              .dateCommis(createRapportTeamLeaderRequest.getDateCommis())
              .laFaute(createRapportTeamLeaderRequest.getLaFaute())
              .salariéMatricule(salarie.getMatricule())
              .temoin(createRapportTeamLeaderRequest.getTemoin())
              .isValidated(createRapportTeamLeaderRequest.isValid())
              .date(new Date().toString())
              .build();


      if(rapportTeamLeaderService.createRapportTeamLeader(rapportTeamLeader,headers)!=null){
         return new ResponseEntity<>("RAPPORT TEAM_LEADER CREATED", HttpStatus.CREATED);
      }
      return new ResponseEntity<>("RAPPORT TEAM_LEADER NOT CREATED", HttpStatus.BAD_REQUEST);
   }

}
