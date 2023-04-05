package com.Younes43.GestionRessourcesHumains.Controllers;

import com.Younes43.GestionRessourcesHumains.Entities.Enums.BusinessUnit;
import com.Younes43.GestionRessourcesHumains.Entities.Enums.TypeContrat;
import com.Younes43.GestionRessourcesHumains.Entities.Requests.CreateSalarieRequest;
import com.Younes43.GestionRessourcesHumains.Entities.Requests.UpdateSalarieRequest;
import com.Younes43.GestionRessourcesHumains.Entities.Salarie;
import com.Younes43.GestionRessourcesHumains.IControllers.ISalarieController;
import com.Younes43.GestionRessourcesHumains.Services.SalarieService;
import lombok.RequiredArgsConstructor;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.Random;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/salarie")
@RequiredArgsConstructor
public class SalarieController implements ISalarieController {
    private final SalarieService salarieService;

    @PostMapping("/create")
    @Override
    public ResponseEntity<Salarie> createSalarie(@RequestBody CreateSalarieRequest createSalarieRequest) {

        Salarie salarie=com.Younes43.GestionRessourcesHumains.Entities.Salarie.builder()
                .nom(createSalarieRequest.getNom())
                .prenom(createSalarieRequest.getPrenom())
                .segment(createSalarieRequest.getSegment())
                .bu(createSalarieRequest.getBu())
                .site(createSalarieRequest.getSite())
                .code_site(createSalarieRequest.getCode_site())
                .departement(createSalarieRequest.getDepartement())
                .local_job_title(createSalarieRequest.getLocal_job_title())
                .position(createSalarieRequest.getPosition())
                .supervisor(createSalarieRequest.getSupervisor())
                .genre(createSalarieRequest.getGenre())
                .type_de_contrat(createSalarieRequest.getType_de_contrat())
                .status(createSalarieRequest.getStatus())
                .build();


        return new ResponseEntity<>(salarieService.createSalarie(salarie), HttpStatus.CREATED);
    }
    @GetMapping("/get/{id}")
    @Override
    public ResponseEntity<Salarie> getSalarie(@PathVariable Long id) {
        Salarie salarie=salarieService.getSalarie(id);
        if(salarie==null){
            return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(salarie,HttpStatus.FOUND);
    }

    @PutMapping("/update")
    @Override
    public ResponseEntity<Salarie> updateSalarie(@RequestBody UpdateSalarieRequest updateSalarieRequest) {
        Salarie salarie=salarieService.getSalarie(updateSalarieRequest.getId());
        if(salarie==null){
            return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
        }
        salarie.setNom(updateSalarieRequest.getNom());
        salarie.setPrenom(updateSalarieRequest.getPrenom());
        salarie.setSegment(updateSalarieRequest.getSegment());
        salarie.setBu(updateSalarieRequest.getBu());
        salarie.setSite(updateSalarieRequest.getSite());
        salarie.setCode_site(updateSalarieRequest.getCode_site());
        salarie.setDepartement(updateSalarieRequest.getDepartement());
        salarie.setLocal_job_title(updateSalarieRequest.getLocal_job_title());
        salarie.setPosition(updateSalarieRequest.getPosition());
        salarie.setSupervisor(updateSalarieRequest.getSupervisor());
        salarie.setGenre(updateSalarieRequest.getGenre());
        salarie.setType_de_contrat(updateSalarieRequest.getType_de_contrat());
        salarie.setStatus(updateSalarieRequest.getStatus());


        return new ResponseEntity<>(salarieService.updateSalarie(salarie),HttpStatus.ACCEPTED);
    }
    @DeleteMapping("/delete/{id}")
    @Override
    public ResponseEntity<String> deleteSalarie( @PathVariable Long id) {
        Salarie salarie=salarieService.getSalarie(id);
        if(salarie==null){
            return new ResponseEntity<>("salarie does not exist",HttpStatus.NOT_FOUND);
        }
        salarieService.deleteSalarie(id);
        return new ResponseEntity<>("deleted successfully",HttpStatus.ACCEPTED);
    }
    @GetMapping("/getAll")
    @Override
    public ResponseEntity<List<Salarie>> getAllSalarie() {

        return new ResponseEntity<>(salarieService.getAllSalarie(),HttpStatus.OK);
    }

    @PostMapping("/upload")
    @Override
    public ResponseEntity<String> uploadSalaries(@RequestPart("file")  MultipartFile file) throws FileNotFoundException {
        try {
            List<Salarie> salaries = new ArrayList<>();
            XSSFWorkbook workbook = new XSSFWorkbook(file.getInputStream());
            XSSFSheet worksheet = workbook.getSheetAt(0);

            for(int i=1; i<worksheet.getPhysicalNumberOfRows(); i++) {

                Salarie salarie = new Salarie();
                XSSFRow row = worksheet.getRow(i);
            
                salarie.setNom(row.getCell(0).getStringCellValue());
                salarie.setMatricule(row.getCell(1).getStringCellValue());
                salarie.setPrenom(row.getCell(2).getStringCellValue());
                salarie.setDate_dembauche(row.getCell(3).getStringCellValue());
                salarie.setSegment(row.getCell(4).getStringCellValue());
                salarie.setBu(BusinessUnit.valueOf(row.getCell(5).getStringCellValue()));
                salarie.setSite(row.getCell(6).getStringCellValue());
                salarie.setCode_site(row.getCell(7).getStringCellValue());
                salarie.setDepartement(row.getCell(8).getStringCellValue());
                salarie.setLocal_job_title(row.getCell(9).getStringCellValue());
                salarie.setPosition(row.getCell(10).getStringCellValue());
                salarie.setSupervisor(row.getCell(11).getStringCellValue());
                salarie.setGenre(row.getCell(12).getStringCellValue());
                salarie.setType_de_contrat(TypeContrat.valueOf(row.getCell(13).getStringCellValue()));
                salarie.setStatus(row.getCell(14).getStringCellValue());

                salaries.add(salarie);
            }

            salarieService.createSalaries(salaries);
            return new ResponseEntity<>("Salaries uploaded successfully", HttpStatus.OK);
        } catch (IOException e) {
            e.printStackTrace();

            return new ResponseEntity<>("Error uploading salaries", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



}
