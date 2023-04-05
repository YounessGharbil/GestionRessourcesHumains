package com.Younes43.GestionRessourcesHumains.Services;

import com.Younes43.GestionRessourcesHumains.Entities.DemandeSanction;
import com.Younes43.GestionRessourcesHumains.Services.DemandeSanctionServices.Utilities.Utilities;
import com.Younes43.GestionRessourcesHumains.IServices.IDemandeSanctionService;
import com.Younes43.GestionRessourcesHumains.Repositories.DemandeSanctionRepository;
import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfWriter;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
public class DemandeSanctionService implements IDemandeSanctionService {
    private final DemandeSanctionRepository demandeSanctionRepository;
    private final GmailService gmailService;
    DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    @Override
    public void generateDemande(HttpServletResponse response, DemandeSanction demandeSanction,String username,String userRole) throws IOException, MessagingException, GeneralSecurityException {


        demandeSanctionRepository.save(demandeSanction);
        gmailService.sendMail(username,"demande de sanction",
                """
                        une demande de sanction est ajouté dans la base de donneé 
                        """);

        Document document=new Document(PageSize.A4);
        PdfWriter.getInstance(document, response.getOutputStream());
        document.open();

        Font fontTitle = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        fontTitle.setSize(18);

        Paragraph title = new Paragraph("DEMANDE DE SANCTION", fontTitle);
        title.setAlignment(Paragraph.ALIGN_CENTER);

        Font fontParagraph = FontFactory.getFont(FontFactory.HELVETICA);
        fontParagraph.setSize(12);

        Paragraph createdBy = new Paragraph("Created By : "+demandeSanction.getCreatedBy(), fontParagraph);
        createdBy.setAlignment(Paragraph.ALIGN_LEFT);

        Paragraph salarieConcerne = new Paragraph("Salarié Concerné : "+demandeSanction.getSalarieConcerne(), fontParagraph);
        salarieConcerne.setAlignment(Paragraph.ALIGN_LEFT);

        Paragraph departement = new Paragraph("Departement : "+demandeSanction.getDepartement(), fontParagraph);
        departement.setAlignment(Paragraph.ALIGN_RIGHT);

        Paragraph date = new Paragraph("Date : "+ LocalDate.now().format(dateFormatter), fontParagraph);
        date.setAlignment(Paragraph.ALIGN_RIGHT);

        Paragraph objet = new Paragraph("Objet : "+demandeSanction.getObjet(), fontParagraph);
        objet.setAlignment(Paragraph.ALIGN_LEFT);

        Paragraph text = new Paragraph(Utilities.DemandeSanctionText, fontParagraph);
        text.setAlignment(Paragraph.ALIGN_LEFT);




        document.add(title);
        document.add(createdBy);
        document.add(salarieConcerne);
        document.add(departement);
        document.add(date);
        document.add(objet);
        document.add(text);
        document.close();



    }

    @Override
    public String validateDemande(Long id,String username,String userRole) throws MessagingException, GeneralSecurityException, IOException {

        DemandeSanction demandeSanction=demandeSanctionRepository.findById(id).isPresent()?
                demandeSanctionRepository.findById(id).get():null;

        switch (userRole) {
            case "ROLE_TEAM_LEADER" -> demandeSanction.setValidatedByTeamLeader(true);
            case "ROLE_SUPERVISEUR" -> demandeSanction.setValidatedBySuperviseur(true);
            case "ROLE_MANAGER" -> demandeSanction.setValidatedByManager(true);
            case "ROLE_RH" -> demandeSanction.setValidatedByRH(true);
            case "ROLE_RH_PLUS1" -> demandeSanction.setValidatedByRHplus1(true);
        }
        demandeSanctionRepository.save(demandeSanction);
        gmailService.sendMail("spike43ultra@gmail.com","demande de sanction",
                """
                        une demande de sanction est validé 
                        """);

        return "demande Validated by "+username+" with role "+userRole;
    }
}
