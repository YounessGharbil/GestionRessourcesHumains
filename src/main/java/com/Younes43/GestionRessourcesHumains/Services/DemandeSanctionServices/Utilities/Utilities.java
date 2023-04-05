package com.Younes43.GestionRessourcesHumains.Services.DemandeSanctionServices.Utilities;

import com.Younes43.GestionRessourcesHumains.Entities.ApplicationUser;
import com.Younes43.GestionRessourcesHumains.Entities.Demande_Sanction.DemandeDeSanction;
import com.Younes43.GestionRessourcesHumains.Entities.Demande_Sanction.IRapport;
import com.Younes43.GestionRessourcesHumains.Entities.Enums.Role;
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


    public static  String  DemandeSanctionText= """
            Lettre recommandée avec accusé de réception
            \s
            Madame, Monsieur,
            A plusieurs reprises - le XXXXX (précisez les dates) - je vous ai fait savoir que je n'appréciais pas certains de vos comportements. En effet, XXXXX (précisez les faits).
            \s
            Ne constatant aucun changement dans votre attitude, je me vois dans l’obligation, par cette lettre, de vous adresser un avertissement.
            \s
            J’espère que cette démarche engendrera des changements dans votre comportement et votre travail. Dans le cas contraire, je serai dans l’obligation de prendre des mesures plus sévères à votre encontre.
            \s
            Nous vous prions d’agréer, Madame, Monsieur, nos respectueuses salutations.
            \s
            Signature
            """;

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
    public  ApplicationUser getSuperior(HashMap<String,String> headers){
        String userRole= headers.get("userRole");
        String site= headers.get("site");
        String department= headers.get("department");
        Role superioRole = switch (userRole) {
            case "ROLE_TEAM_LEADER" -> Role.ROLE_SUPERVISEUR;
            case "ROLE_SUPERVISEUR" -> Role.ROLE_MANAGER;
            case "ROLE_MANAGER" -> Role.ROLE_RH;
            case "ROLE_RH" -> Role.ROLE_RH_PLUS1;
            default -> null;
        };
        ApplicationUser superior=userRepository.findByRoleAndDepartmentAndSite(superioRole,department,site).isPresent()?
                userRepository.findByRoleAndDepartmentAndSite(superioRole,department,site).get():null;
        return superior;
    }
    public void validateDemande(IRapport rapport,HashMap<String,String> headers){
        DemandeDeSanction savedDemandeDeSanction=rapport.getDemandeDeSanction();
        if(rapport.isValidated()){
            switch (headers.get("userRole")) {
                case "ROLE_TEAM_LEADER" -> savedDemandeDeSanction.setTeamLeaderValidation(true);
                case "ROLE_SUPERVISEUR" -> savedDemandeDeSanction.setSuperviseurValidation(true);
                case "ROLE_MANAGER" -> savedDemandeDeSanction.setManagerValidation(true);
                case "ROLE_RH" -> savedDemandeDeSanction.setRhValidation(true);
                case "ROLE_RH_PLUS1" -> savedDemandeDeSanction.setRhPlus1Validation(true);
            }
            demandeDeSanctionRepository.save(savedDemandeDeSanction);
        }
    }
    public static String getValidationMSG(boolean validation){
        String validationmsg=validation?"and validated":"and not validated";
        return  validationmsg;
    }
    public void sendMailToSuperior(IRapport rapport,HashMap<String,String> headers) throws MessagingException, GeneralSecurityException, IOException {
        validateDemande(rapport, headers);
        ApplicationUser superior = getSuperior(headers);
        if (superior != null) {
            String validationMSG = Utilities.getValidationMSG(rapport.isValidated());
            gmailService.sendMail(superior.getEmail(), "demande created",
                    "demande created " + validationMSG
                            + " by " + rapport.getUserMatricule() + " with id="
                            + rapport.getDemandeDeSanction().getId());
        }
    }
}

