package com.Younes43.GestionRessourcesHumains.Controllers.DemandeDeSanctionController;
import org.springframework.http.HttpStatus;
import com.Younes43.GestionRessourcesHumains.Entities.Demande_Sanction.*;
import com.Younes43.GestionRessourcesHumains.Entities.Responses.GetAllDemnadeDeSanctionResponse;
import com.Younes43.GestionRessourcesHumains.IControllers.IDemandeDeSanctionController;
import com.Younes43.GestionRessourcesHumains.Entities.Salarie;
import com.Younes43.GestionRessourcesHumains.Repositories.DemandeSanctionRepositories.*;
import com.Younes43.GestionRessourcesHumains.Services.DemandeSanctionServices.DemandeDeSanctionService;
import com.Younes43.GestionRessourcesHumains.Services.DemandeSanctionServices.Utilities.Utilities;
import com.Younes43.GestionRessourcesHumains.Services.SalarieService;
import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfWriter;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.io.*;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/demandeDeSanction")
@RequiredArgsConstructor
public class DemandeDeSanctionController implements IDemandeDeSanctionController {
    DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    private final Utilities utilities;
    private final DemandeDeSanctionService demandeDeSanctionService;
    private final SalarieService salarieService;
    String desktopPath = System.getProperty("user.home") + "/Desktop/";
    String fileName = "DemandeDeSanction.pdf";


    @GetMapping(value = "demandePdf/{id}",produces = "application/pdf")
    public void getDemandePdf(HttpServletRequest request, HttpServletResponse response, @PathVariable Long id) throws IOException {

        DemandeDeSanction demandeDeSanction=demandeDeSanctionService.getDemandeDeSanction(id);

        RAPPORT_TEAM_LEADER rapportTeamLeader=demandeDeSanction.getRapportTeamLeader();
        RAPPORT_SUPERVISEUR rapportSuperviseur=demandeDeSanction.getRapportSuperviseur();
        RAPPORT_RH rapportRh=demandeDeSanction.getRapportRh();
        RAPPORT_RHPLUS1 rapportRhplus1=demandeDeSanction.getRapportRhplus1();
        RAPPORT_MANAGER rapportManager=demandeDeSanction.getRapportManager();
   



        Document document=new Document(PageSize.A4);
        PdfWriter.getInstance(document, response.getOutputStream());


        document.open();

        Font fontParagraph = FontFactory.getFont(FontFactory.COURIER);
        fontParagraph.setSize(12);

        Font fontTitle = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        fontTitle.setSize(18);

        Font fontTitle2 = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        fontTitle.setSize(14);

        Paragraph title1 = new Paragraph("Service des Ressources Humaines:", fontTitle2);
        title1.setAlignment(Paragraph.ALIGN_LEFT);

        Paragraph title2 = new Paragraph("DEMANDE DE SANCTION", fontTitle);
        title2.setAlignment(Paragraph.ALIGN_CENTER);

        document.add(new Paragraph(" "));
        document.add(title1);
        document.add(new Paragraph(" "));
        document.add(title2);
        document.add(new Paragraph(" "));
        document.add(new Paragraph(" "));



        Paragraph rapport_TeamLeader = new Paragraph("1) RAPPORT DU CHEF DIRECT", fontTitle2);
        rapport_TeamLeader.setAlignment(Paragraph.ALIGN_LEFT);

        Salarie salarieConcerné=salarieService.getSalarieBySalarieId(rapportTeamLeader.getSalariéMatricule());
        Salarie user=salarieService.getSalarieBySalarieId(rapportTeamLeader.getUserMatricule());


        Paragraph salarie = new Paragraph("M.(Mle.) : "
                +salarieConcerné.getNom()+" "+salarieConcerné.getPrenom()+" | Fonction:"
                +salarieConcerné.getPosition(), fontParagraph);
        salarie.setAlignment(Paragraph.ALIGN_LEFT);

        Paragraph section = new Paragraph("Section : "+salarieConcerné.getSegment()+"| A commis le : "
                +rapportTeamLeader.getDateCommis(), fontParagraph);
        section.setAlignment(Paragraph.ALIGN_LEFT);


        Paragraph faute = new Paragraph("La faute suivante: "+rapportTeamLeader.getLaFaute(), fontParagraph);
        faute.setAlignment(Paragraph.ALIGN_LEFT);



        Paragraph témoins = new Paragraph("En présence des témoins:"+"temoins", fontParagraph);
        témoins.setAlignment(Paragraph.ALIGN_LEFT);

        Paragraph contreMaitre = new Paragraph("Nom du Contremaître:"+user.getNom()+" "+user.getPrenom(), fontParagraph);
        contreMaitre.setAlignment(Paragraph.ALIGN_LEFT);

        Paragraph dateRapportTeamLeader= new Paragraph("Date : "+rapportTeamLeader.getDate(), fontParagraph);
        dateRapportTeamLeader.setAlignment(Paragraph.ALIGN_RIGHT);

        document.add(rapport_TeamLeader);
        document.add(new Paragraph(" "));
        document.add(salarie);
        document.add(section);
        document.add(faute);
        document.add(témoins);
        document.add(contreMaitre);
        document.add(dateRapportTeamLeader);
        document.add(new Paragraph(" "));

        if(rapportSuperviseur!=null){
            Paragraph rapport_Superviseur = new Paragraph("2)AVIS DU SHIFT LEADER", fontTitle2);
            rapport_Superviseur.setAlignment(Paragraph.ALIGN_LEFT);
    
            Paragraph avisSuperViseur = new Paragraph(rapportSuperviseur.getAvis(), fontParagraph);
            avisSuperViseur.setAlignment(Paragraph.ALIGN_LEFT);
    
            Paragraph sanctionSuperViseur = new Paragraph("Sanction demandée : "+rapportSuperviseur.getSanctionDemandé(), fontParagraph);
            sanctionSuperViseur.setAlignment(Paragraph.ALIGN_LEFT);
    
            Paragraph dateSuperViseur = new Paragraph("Date : "+rapportSuperviseur.getDate(), fontParagraph);
            dateSuperViseur.setAlignment(Paragraph.ALIGN_RIGHT);
    
            document.add(rapport_Superviseur);
            document.add(new Paragraph(" "));
            document.add(avisSuperViseur);
            document.add(sanctionSuperViseur);
            document.add(dateSuperViseur);
            document.add(new Paragraph(" "));
        }

       

        if(rapportManager!=null){
            Paragraph rapport_Manager = new Paragraph("3) AVIS DU CHEF DE DEPARTEMENT", fontTitle2);
            rapport_Manager.setAlignment(Paragraph.ALIGN_LEFT);
    
            Paragraph avisManager = new Paragraph(rapportManager.getAvisManager(), fontParagraph);
            avisManager.setAlignment(Paragraph.ALIGN_LEFT);
    
            Paragraph sanctionManager = new Paragraph("Sanction demandée : "+rapportManager.getSanctionDemandé(), fontParagraph);
            sanctionManager.setAlignment(Paragraph.ALIGN_LEFT);
    
            Paragraph dateManager = new Paragraph("Date : "+rapportManager.getDate(), fontParagraph);
            dateManager.setAlignment(Paragraph.ALIGN_RIGHT);
    
            document.add(rapport_Manager);
            document.add(new Paragraph(" "));
            document.add(avisManager);
            document.add(sanctionManager);
            document.add(dateManager);
            document.add(new Paragraph(" "));
        }

       

        Paragraph dateEmbauche = new Paragraph(
                "4) Date D’embauche:"+rapportRh.getDateEmbauche(), fontTitle2);
        dateEmbauche.setAlignment(Paragraph.ALIGN_LEFT);

        Paragraph age = new Paragraph("Age:"+rapportRh.getAge(), fontParagraph);
        age.setAlignment(Paragraph.ALIGN_LEFT);

        Paragraph nbreEnfants = new Paragraph("Nbre d’enfants :"+rapportRh.getNbrEnfants(), fontParagraph);
        nbreEnfants.setAlignment(Paragraph.ALIGN_LEFT);

        document.add(dateEmbauche);
        document.add(age);
        document.add(nbreEnfants);
        document.add(new Paragraph(" "));


        Paragraph rapportRHPLUS1 = new Paragraph("5) DECISION DE LA DIRECTION", fontTitle2);
        rapportRHPLUS1.setAlignment(Paragraph.ALIGN_LEFT);

        Paragraph avisRHPLUS1 = new Paragraph(rapportRhplus1.getCommentaire(), fontParagraph);
        avisRHPLUS1.setAlignment(Paragraph.ALIGN_LEFT);

        Paragraph decisionFinal = new Paragraph("DECISION FINALE: "+rapportRhplus1.getDecisionFinal(), fontParagraph);
        decisionFinal.setAlignment(Paragraph.ALIGN_LEFT);

        Paragraph dateRHPLUS1 = new Paragraph("Date : "+rapportRhplus1.getDate(), fontParagraph);
        dateRHPLUS1.setAlignment(Paragraph.ALIGN_RIGHT);

        document.add(rapportRHPLUS1);
        document.add(new Paragraph(" "));
        document.add(avisRHPLUS1);
        document.add(decisionFinal);
        document.add(dateRHPLUS1);

        document.close();


    }

//     @GetMapping("/getAllDemandes")
//     @Override
//     public ResponseEntity<List<GetAllDemnadeDeSanctionResponse>> getAllDemandes() {
//         List<GetAllDemnadeDeSanctionResponse> AllDemandes = demandeDeSanctionService.getAllDemandes()
//         .stream()
//         .filter(demande -> demande.getRapportManager() != null && demande.getRapportSuperviseur() != null &&  demande.getRapportRh() != null && demande.getRapportRhplus1() != null)
//         .map(demande -> {
//             return GetAllDemnadeDeSanctionResponse.builder()
//                     .id(demande.getId())
//                     .salarie(demande.getSalarie().getId())
//                     .user(demande.getUser().getId())
//                     .teamLeaderValidation(demande.isTeamLeaderValidation())
//                     .superviseurValidation(demande.isSuperviseurValidation())
//                     .managerValidation(demande.isManagerValidation())
//                     .rhPlus1Validation(demande.isRhPlus1Validation())
//                     .rhValidation(demande.isRhValidation())
//                     .demandeStatus(demande.getDemandeStatus())
//                     .niveauDeTraitement(demande.getNiveauDeTraitement())
//                     .rapportTeamLeader(demande.getRapportTeamLeader().getId())
//                     .rapportSuperviseur(demande.getRapportSuperviseur().getId())
//                     .rapportManager(demande.getRapportManager().getId())
//                     .rapportRh(demande.getRapportRh().getId())
//                     .rapportRhplus1(demande.getRapportRhplus1().getId())
//                     .build();
//         })
//         .collect(Collectors.toList());

// return new ResponseEntity<>(AllDemandes, HttpStatus.OK);
//     }
@GetMapping("/getAllDemandes")
@Override
public ResponseEntity<List<GetAllDemnadeDeSanctionResponse>> getAllDemandes() {
    System.out.println("inside get all demandes");
    List<GetAllDemnadeDeSanctionResponse> AllDemandes = new ArrayList<>();
    
    for (DemandeDeSanction demande : demandeDeSanctionService.getAllDemandes()) {
     
        AllDemandes.add(
            GetAllDemnadeDeSanctionResponse.builder()
                .id(demande.getId())
                .salarie(demande.getSalarie().getId())
                .user(demande.getUser().getId())
                .teamLeaderValidation(demande.isTeamLeaderValidation())
                .superviseurValidation(demande.isSuperviseurValidation())
                .managerValidation(demande.isManagerValidation())
                .rhPlus1Validation(demande.isRhPlus1Validation())
                .rhValidation(demande.isRhValidation())
                .demandeStatus(demande.getDemandeStatus())
                .niveauDeTraitement(demande.getNiveauDeTraitement())
                .rapportTeamLeader(demande.getRapportTeamLeader().getId())
                .rapportSuperviseur(demande.getRapportSuperviseur() != null ? demande.getRapportSuperviseur().getId() : null)
                .rapportManager(demande.getRapportManager() != null ? demande.getRapportManager().getId() : null)
                .rapportRh(demande.getRapportRh().getId())
                .rapportRhplus1(demande.getRapportRhplus1().getId())
                .departement(demande.getDepartement())
                .site(demande.getSite())
                .build()
        );
    }
    return new ResponseEntity<>(AllDemandes, HttpStatus.OK);
}


    @GetMapping("/get/{id}")
    @Override
    public ResponseEntity<GetAllDemnadeDeSanctionResponse> getDemande(@PathVariable Long id) {
        DemandeDeSanction demande=demandeDeSanctionService.getDemandeDeSanction(id);
        GetAllDemnadeDeSanctionResponse response=GetAllDemnadeDeSanctionResponse.builder()
        .id(demande.getId())
        .salarie(demande.getSalarie().getId())
        .user(demande.getUser().getId())
        .teamLeaderValidation(demande.isTeamLeaderValidation())
        .superviseurValidation(demande.isSuperviseurValidation())
        .managerValidation(demande.isManagerValidation())
        .rhValidation(demande.isRhValidation())
        .rhPlus1Validation(demande.isRhPlus1Validation())
        .demandeStatus(demande.getDemandeStatus())
        .niveauDeTraitement(demande.getNiveauDeTraitement())
        .departement(demande.getDepartement())
        .site(demande.getSite())
        .build();

       return new ResponseEntity<>(response,HttpStatus.OK);
    }
}
