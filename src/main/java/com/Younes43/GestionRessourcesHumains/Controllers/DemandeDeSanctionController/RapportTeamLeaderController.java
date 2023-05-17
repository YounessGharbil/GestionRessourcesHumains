package com.Younes43.GestionRessourcesHumains.Controllers.DemandeDeSanctionController;

import com.Younes43.GestionRessourcesHumains.Entities.ApplicationUser;
import com.Younes43.GestionRessourcesHumains.Entities.Demande_Sanction.DemandeDeSanction;
import com.Younes43.GestionRessourcesHumains.Entities.Demande_Sanction.RAPPORT_TEAM_LEADER;
import com.Younes43.GestionRessourcesHumains.Entities.Enums.DemandeStatus;
import com.Younes43.GestionRessourcesHumains.Entities.Requests.CreateDemandeDeSanctionRequest;
import com.Younes43.GestionRessourcesHumains.Entities.Requests.CreateRapportTeamLeaderRequest;
import com.Younes43.GestionRessourcesHumains.Entities.Responses.GetAllRapportTeamLeaderResponse;
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
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;


@CrossOrigin(origins = "http://localhost:4200")
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
      SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");



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
              .date(dateFormat.format(new Date()).toString())
              .build();


      if(rapportTeamLeaderService.createRapportTeamLeader(rapportTeamLeader,headers)!=null){
         return new ResponseEntity<>("RAPPORT TEAM_LEADER CREATED", HttpStatus.CREATED);
      }
      return new ResponseEntity<>("RAPPORT TEAM_LEADER NOT CREATED", HttpStatus.BAD_REQUEST);
   }

    @GetMapping("/getAll")
    @Override
    public ResponseEntity<List<GetAllRapportTeamLeaderResponse>> getAllRapportTeamLeader() {
        List<GetAllRapportTeamLeaderResponse> AllRapportTeamLeader=new ArrayList<>();
        for(RAPPORT_TEAM_LEADER rapport:rapportTeamLeaderService.getAllRapportTeamLeader()){
            AllRapportTeamLeader.add(
                GetAllRapportTeamLeaderResponse.builder()
                .id(rapport.getId())
                .date(rapport.getDate())
                .date_commis(rapport.getDateCommis())
                .escalated_to_rh(rapport.isEscalatedToRh())
                .fonction(rapport.getFonction())
                .is_validated(rapport.isValidated())
                .la_faut(rapport.getLaFaute())
                .processed_by_superviseur(rapport.isProcessedBySuperviseur())
                .salarie_matricule(rapport.getSalariéMatricule())
                .section(rapport.getSection())
                .temoin(rapport.getTemoin())
                .user_matricule(rapport.getUserMatricule())
                .demande_de_sanction_id(rapport.getDemandeDeSanction().getId())
                .build()
                ) ;
        }
        
        
        return new ResponseEntity<>(AllRapportTeamLeader,HttpStatus.OK);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<GetAllRapportTeamLeaderResponse> getRapportTeamLeader(@PathVariable Long id) {
        RAPPORT_TEAM_LEADER RapportTeamLeader=rapportTeamLeaderService.getRapportTeamLeader(id);
        if(RapportTeamLeader==null){
            return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
        }
        GetAllRapportTeamLeaderResponse response=GetAllRapportTeamLeaderResponse.builder()
        .id(RapportTeamLeader.getId())
        .demande_de_sanction_id(RapportTeamLeader.getDemandeDeSanction().getId())
        .date(RapportTeamLeader.getDate())
        .date_commis(RapportTeamLeader.getDateCommis())
        .escalated_to_rh(RapportTeamLeader.isEscalatedToRh())
        .fonction(RapportTeamLeader.getFonction())
        .is_validated(RapportTeamLeader.isValidated())
        .la_faut(RapportTeamLeader.getLaFaute())
        .processed_by_superviseur(RapportTeamLeader.isProcessedBySuperviseur())
        .salarie_matricule(RapportTeamLeader.getSalariéMatricule())
        .section(RapportTeamLeader.getSection())
        .temoin(RapportTeamLeader.getTemoin())
        .user_matricule(RapportTeamLeader.getUserMatricule())
        .build();
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @GetMapping("/getAllNotProcessed")
    @Override
    public ResponseEntity<List<GetAllRapportTeamLeaderResponse>> getRapportsTeamLeaderNotProcessedByManager() {
        List<GetAllRapportTeamLeaderResponse> rapportsTeamLeader=new ArrayList<>();
        for(RAPPORT_TEAM_LEADER rapport:rapportTeamLeaderService.getRapportsTeamLeaderNotProcessedBySperviseur()){
            rapportsTeamLeader.add(
                GetAllRapportTeamLeaderResponse.builder()
                .id(rapport.getId())
                .date(rapport.getDate())
                .date_commis(rapport.getDateCommis())
                .escalated_to_rh(rapport.isEscalatedToRh())
                .fonction(rapport.getFonction())
                .is_validated(rapport.isValidated())
                .la_faut(rapport.getLaFaute())
                .processed_by_superviseur(rapport.isProcessedBySuperviseur())
                .salarie_matricule(rapport.getSalariéMatricule())
                .section(rapport.getSection())
                .temoin(rapport.getTemoin())
                .user_matricule(rapport.getUserMatricule())
                .demande_de_sanction_id(rapport.getDemandeDeSanction().getId())
                .build()
                ) ;
        }
        return new ResponseEntity<>(rapportsTeamLeader,HttpStatus.OK);
    }
}
