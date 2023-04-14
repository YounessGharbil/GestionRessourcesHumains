package com.Younes43.GestionRessourcesHumains.Controllers.DemandeDeSanctionController;

import com.Younes43.GestionRessourcesHumains.Entities.Demande_Sanction.*;
import com.Younes43.GestionRessourcesHumains.Entities.Salarie;
import com.Younes43.GestionRessourcesHumains.Repositories.DemandeSanctionRepositories.*;
import com.Younes43.GestionRessourcesHumains.Services.DemandeSanctionServices.DemandeDeSanctionService;
import com.Younes43.GestionRessourcesHumains.Services.DemandeSanctionServices.Utilities.Utilities;
import com.Younes43.GestionRessourcesHumains.Services.SalarieService;
import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfWriter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping("/demandeDeSanction")
@RequiredArgsConstructor
public class DemandeDeSanctionController {
    DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    private final Utilities utilities;
    private final DemandeDeSanctionService demandeDeSanctionService;
    private final SalarieService salarieService;
    @GetMapping("demandePdf/{id}")
    public  void getDemandePdf(HttpServletRequest request, HttpServletResponse response,@PathVariable Long id) throws IOException {

        DemandeDeSanction demandeDeSanction=demandeDeSanctionService.getDemandeDeSanction(id);

        RAPPORT_TEAM_LEADER rapportTeamLeader=demandeDeSanction.getRapportTeamLeader();
        RAPPORT_SUPERVISEUR rapportSuperviseur=demandeDeSanction.getRapportSuperviseur();
        RAPPORT_RH rapportRh=demandeDeSanction.getRapportRh();
        RAPPORT_RHPLUS1 rapportRhplus1=demandeDeSanction.getRapportRhplus1();
        RAPPORT_MANAGER rapportManager;
        if(demandeDeSanction.getRapportManager()!=null){
            rapportManager=demandeDeSanction.getRapportManager();
            }
        else{
            rapportManager=RAPPORT_MANAGER.builder().avisManager("...").sanctionDemandé("...").build();
        }





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

        Paragraph rapport_Superviseur = new Paragraph("2)AVIS DU SHIFT LEADER", fontTitle2);
        rapport_Superviseur.setAlignment(Paragraph.ALIGN_LEFT);

        Paragraph avisSuperViseur = new Paragraph(rapportSuperviseur.getAvis(), fontParagraph);
        avisSuperViseur.setAlignment(Paragraph.ALIGN_LEFT);

        Paragraph sanctionSuperViseur = new Paragraph("Sanction demandée : "+rapportSuperviseur.getSanctionDemandé(), fontParagraph);
        sanctionSuperViseur.setAlignment(Paragraph.ALIGN_LEFT);

        Paragraph dateSuperViseur = new Paragraph("Date : "+rapportSuperviseur.getDate(), fontParagraph);
        dateSuperViseur.setAlignment(Paragraph.ALIGN_RIGHT);

        Paragraph rapport_Manager = new Paragraph("3) AVIS DU CHEF DE DEPARTEMENT", fontTitle2);
        rapport_Manager.setAlignment(Paragraph.ALIGN_LEFT);

        Paragraph avisManager = new Paragraph(rapportManager.getAvisManager(), fontParagraph);
        avisManager.setAlignment(Paragraph.ALIGN_LEFT);

        Paragraph sanctionManager = new Paragraph("Sanction demandée : "+rapportManager.getSanctionDemandé(), fontParagraph);
        sanctionManager.setAlignment(Paragraph.ALIGN_LEFT);

        Paragraph dateManager = new Paragraph("Date : "+rapportManager.getDate(), fontParagraph);
        dateManager.setAlignment(Paragraph.ALIGN_RIGHT);

        Paragraph dateEmbauche = new Paragraph(
                "4) Date D’embauche:"+rapportRh.getDateEmbauche(), fontTitle2);
        dateEmbauche.setAlignment(Paragraph.ALIGN_LEFT);

        Paragraph age = new Paragraph("Age:"+rapportRh.getAge(), fontParagraph);
        age.setAlignment(Paragraph.ALIGN_LEFT);

        Paragraph nbreEnfants = new Paragraph("Nbre d’enfants :"+rapportRh.getNbrEnfants(), fontParagraph);
        nbreEnfants.setAlignment(Paragraph.ALIGN_LEFT);


        Paragraph rapportRHPLUS1 = new Paragraph("5) DECISION DE LA DIRECTION", fontTitle2);
        rapportRHPLUS1.setAlignment(Paragraph.ALIGN_LEFT);

        Paragraph avisRHPLUS1 = new Paragraph(rapportRhplus1.getCommentaire(), fontParagraph);
        avisRHPLUS1.setAlignment(Paragraph.ALIGN_LEFT);

        Paragraph decisionFinal = new Paragraph("DECISION FINALE: "+rapportRhplus1.getDecisionFinal(), fontParagraph);
        decisionFinal.setAlignment(Paragraph.ALIGN_LEFT);

        Paragraph dateRHPLUS1 = new Paragraph("Date : "+rapportRhplus1.getDate(), fontParagraph);
        dateRHPLUS1.setAlignment(Paragraph.ALIGN_RIGHT);


        document.add(new Paragraph(" "));
        document.add(title1);
        document.add(new Paragraph(" "));
        document.add(title2);
        document.add(new Paragraph(" "));
        document.add(new Paragraph(" "));

        document.add(rapport_TeamLeader);
        document.add(new Paragraph(" "));
        document.add(salarie);
        document.add(new Paragraph(" "));
        document.add(section);
        document.add(new Paragraph(" "));
        document.add(faute);
        document.add(new Paragraph(" "));
        document.add(témoins);
        document.add(new Paragraph(" "));
        document.add(contreMaitre);
        document.add(new Paragraph(" "));
        document.add(dateRapportTeamLeader);
        document.add(new Paragraph(" "));
        document.add(rapport_Superviseur);
        document.add(new Paragraph(" "));
        document.add(avisSuperViseur);
        document.add(new Paragraph(" "));
        document.add(sanctionSuperViseur);
        document.add(new Paragraph(" "));
        document.add(dateSuperViseur);
        document.add(new Paragraph(" "));
        document.add(rapport_Manager);
        document.add(new Paragraph(" "));
        document.add(avisManager);
        document.add(new Paragraph(" "));
        document.add(sanctionManager);
        document.add(new Paragraph(" "));
        document.add(dateManager);
        document.add(new Paragraph(" "));
        document.add(dateEmbauche);
        document.add(age);
        document.add(nbreEnfants);
        document.add(new Paragraph(" "));
        document.add(rapportRHPLUS1);
        document.add(new Paragraph(" "));
        document.add(avisRHPLUS1);
        document.add(new Paragraph(" "));
        document.add(decisionFinal);
        document.add(new Paragraph(" "));
        document.add(dateRHPLUS1);
        document.close();


    }
}
