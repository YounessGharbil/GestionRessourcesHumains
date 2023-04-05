package com.Younes43.GestionRessourcesHumains.Controllers;

import com.Younes43.GestionRessourcesHumains.Entities.DemandeSanction;
import com.Younes43.GestionRessourcesHumains.IControllers.IDemandeSanctionController;
import com.Younes43.GestionRessourcesHumains.Services.DemandeSanctionService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


@RestController
@RequestMapping("/demandeSanction")
@RequiredArgsConstructor
public class DemandeSanctionController implements IDemandeSanctionController {


    private final DemandeSanctionService demandeSanctionService;

    @PostMapping("/generateDemande")
    @Override
    public ResponseEntity<String> generateDemande(HttpServletRequest request,HttpServletResponse response, @RequestBody DemandeSanction demandeSanction) throws IOException, MessagingException, GeneralSecurityException {
        String username= request.getHeader("username");
        String userRole= request.getHeader("userRole");
        response.setContentType("application/pdf");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd:hh:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=pdf_" + currentDateTime + ".pdf";
        response.setHeader(headerKey, headerValue);
        response.setHeader("Content-Language", "ar, en-US");
        response.setHeader("Accept-Language", "ar, en-US");
        demandeSanctionService.generateDemande(response,demandeSanction,username,userRole);
        return new ResponseEntity<>("demande generated successfully", HttpStatus.ACCEPTED);
    }

    @PutMapping("/validate/{id}")
    @Override
    public ResponseEntity<String> validateDemande(HttpServletRequest request, @PathVariable Long id) throws MessagingException, GeneralSecurityException, IOException {
        //String token=request.getHeader("Authorization").substring(7);
        String username= request.getHeader("username");
        String userRole= request.getHeader("userRole");
         demandeSanctionService.validateDemande(id,username,userRole);
        return new ResponseEntity<>("demande generated successfully", HttpStatus.ACCEPTED);
    }

}
