package com.Younes43.GestionRessourcesHumains.Services.DemandeSanctionServices.Utilities;

import com.Younes43.GestionRessourcesHumains.Entities.ApplicationUser;
import com.Younes43.GestionRessourcesHumains.Entities.Demande_Sanction.DemandeDeSanction;
import com.Younes43.GestionRessourcesHumains.Entities.Demande_Sanction.IRapport;
import com.Younes43.GestionRessourcesHumains.Entities.Demande_Sanction.RAPPORT_MANAGER;
import com.Younes43.GestionRessourcesHumains.Entities.Demande_Sanction.RAPPORT_RH;
import com.Younes43.GestionRessourcesHumains.Entities.Demande_Sanction.RAPPORT_RHPLUS1;
import com.Younes43.GestionRessourcesHumains.Entities.Demande_Sanction.RAPPORT_SUPERVISEUR;
import com.Younes43.GestionRessourcesHumains.Entities.Demande_Sanction.RAPPORT_TEAM_LEADER;
import com.Younes43.GestionRessourcesHumains.Entities.Enums.*;
import com.Younes43.GestionRessourcesHumains.Entities.Salarie;
import com.Younes43.GestionRessourcesHumains.Repositories.DemandeSanctionRepositories.DemandeDeSanctionRepository;
import com.Younes43.GestionRessourcesHumains.Repositories.UserRepository;
import com.Younes43.GestionRessourcesHumains.Services.GmailService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class Utilities {
    private final UserRepository userRepository;
    private final DemandeDeSanctionRepository demandeDeSanctionRepository;
    private final GmailService gmailService;


    

    public static HashMap<String,String> extractHeaders(HttpServletRequest request){

        String username= request.getHeader("username");
        String userRole= request.getHeader("userRole");
        String matricule= request.getHeader("matricule");
        String site= request.getHeader("site");
        String department= request.getHeader("department");
        HashMap<String,String> headers=new HashMap<>();
        headers.put("username",username);
        headers.put("userRole",userRole);
        headers.put("matricule",matricule);
        headers.put("site",site);
        headers.put("department",department);
        return headers;
    }
    public  ApplicationUser getSuperior(IRapport rapport, HashMap<String,String> headers){

        Salarie salarie=rapport.getDemandeDeSanction().getSalarie();
        String userRole= headers.get("userRole");
        String site= headers.get("site");
        String department= headers.get("department");
        Role superioRole;
        

        switch (userRole) {
            case "ROLE_TEAM_LEADER" -> superioRole = Role.ROLE_SUPERVISEUR;
            case "ROLE_SUPERVISEUR" -> {
                if (salarie.getDirect().equalsIgnoreCase("DIRECT")) {
                    superioRole = Role.ROLE_RH;
                } else {
                    superioRole = Role.ROLE_MANAGER;
                }
            }
            case "ROLE_MANAGER" -> superioRole = Role.ROLE_RH;
            case "ROLE_RH" -> superioRole = Role.ROLE_RH_PLUS1;
            default -> superioRole = null;
        }
   

        ApplicationUser superior=userRepository.findByRoleAndDepartmentAndSite(superioRole,department,site).isPresent()?
                userRepository.findByRoleAndDepartmentAndSite(superioRole,department,site).get():null;
             
        return superior;
    }

    // public boolean isDirect(Salarie salarie) {
    //     return salarie.getDirect().equalsIgnoreCase("TRUE");
    // }

    public void validateDemande(IRapport rapport,HashMap<String,String> headers){
        DemandeDeSanction savedDemandeDeSanction=rapport.getDemandeDeSanction();
        if(rapport.isValidated()){
            switch (headers.get("userRole")) {
                case "ROLE_TEAM_LEADER" -> {
                   
                    savedDemandeDeSanction.setTeamLeaderValidation(true);
                    savedDemandeDeSanction.setNiveauDeTraitement(NiveauDeTraitement.TEAM_LEADER.name());
                    
                }
                case "ROLE_SUPERVISEUR" -> {
                    savedDemandeDeSanction.setSuperviseurValidation(true);
                    savedDemandeDeSanction.setNiveauDeTraitement(NiveauDeTraitement.SUPERVISEUR.name());
                }
                case "ROLE_MANAGER" -> {
                    savedDemandeDeSanction.setManagerValidation(true);
                    savedDemandeDeSanction.setNiveauDeTraitement(NiveauDeTraitement.MANAGER.name());
                }
                case "ROLE_RH" -> {
                    savedDemandeDeSanction.setRhValidation(true);
                    savedDemandeDeSanction.setNiveauDeTraitement(NiveauDeTraitement.RH.name());
                }
                case "ROLE_RH_PLUS1" -> {
                    savedDemandeDeSanction.setRhPlus1Validation(true);
                    savedDemandeDeSanction.setNiveauDeTraitement(NiveauDeTraitement.RH_PLUS1.name());
                }
            }
        }
        else {
            switch (headers.get("userRole")) {
                case "ROLE_TEAM_LEADER" -> {
                    savedDemandeDeSanction.setNiveauDeTraitement(NiveauDeTraitement.TEAM_LEADER.name());
                }
                case "ROLE_SUPERVISEUR" -> {
                    savedDemandeDeSanction.setNiveauDeTraitement(NiveauDeTraitement.SUPERVISEUR.name());
                }
                case "ROLE_MANAGER" -> {
                    savedDemandeDeSanction.setNiveauDeTraitement(NiveauDeTraitement.MANAGER.name());
                }
                case "ROLE_RH" -> {
                    savedDemandeDeSanction.setDemandeStatus(DemandeStatus.Refusé.name());
                    savedDemandeDeSanction.setNiveauDeTraitement(NiveauDeTraitement.RH.name());
                }
                case "ROLE_RH_PLUS1" -> {
                    savedDemandeDeSanction.setDemandeStatus(DemandeStatus.Refusé.name());
                    savedDemandeDeSanction.setNiveauDeTraitement(NiveauDeTraitement.RH_PLUS1.name());
                }
            }
        }
        demandeDeSanctionRepository.save(savedDemandeDeSanction);



    }
    public static String getValidationMSG(boolean validation){
        String validationmsg=validation?"and validated":"and not validated";
        return  validationmsg;
    }
    public void sendMailToSuperior(IRapport rapport,HashMap<String,String> headers) throws MessagingException, GeneralSecurityException, IOException {

        validateDemande(rapport, headers);
        ApplicationUser superior = getSuperior(rapport, headers);
        DemandeDeSanction demandeDeSanction=rapport.getDemandeDeSanction();

        if (superior != null && !(headers.get("userRole").equals("ROLE_RH") && !rapport.isValidated()) ) {

            String validationMSG = Utilities.getValidationMSG(rapport.isValidated());
            gmailService.sendMail(superior.getEmail(), "demande created",
                    "demande created " + validationMSG
                            + " by " + rapport.getUserMatricule() + " with id="
                            + rapport.getDemandeDeSanction().getId());

        } else if (rapport.isValidated()) {
            demandeDeSanction.setDemandeStatus(DemandeStatus.Validé.name());
        }

        demandeDeSanctionRepository.save(demandeDeSanction);
    }

    public void notifierRh(IRapport rapport) throws MessagingException, GeneralSecurityException, IOException {
        ApplicationUser user=userRepository.
                findByMatricule(rapport.getUserMatricule()).get();

        ApplicationUser rh=userRepository.
                findByRoleAndDepartmentAndSite(Role.ROLE_RH,user.getDepartment(), user.getSite()).get();

        gmailService.sendMail(rh.getEmail(), "demande escalation",
                "demande not processed by "
                        +" "+user.getUsername()+" "
                        + rapport.getUserMatricule() + " with id="
                        + rapport.getDemandeDeSanction().getId());


    }


}

