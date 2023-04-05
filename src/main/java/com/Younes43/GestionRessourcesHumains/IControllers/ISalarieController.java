package com.Younes43.GestionRessourcesHumains.IControllers;

import com.Younes43.GestionRessourcesHumains.Entities.Requests.CreateSalarieRequest;
import com.Younes43.GestionRessourcesHumains.Entities.Requests.UpdateSalarieRequest;
import com.Younes43.GestionRessourcesHumains.Entities.Salarie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.util.List;

public interface ISalarieController {
     ResponseEntity<Salarie> createSalarie( CreateSalarieRequest createSalarieRequest);
     ResponseEntity<Salarie> getSalarie( Long id);
     ResponseEntity<Salarie> updateSalarie( UpdateSalarieRequest updateSalarieRequest);
     ResponseEntity<String> deleteSalarie( Long id);
     ResponseEntity<List<Salarie>> getAllSalarie();
     ResponseEntity<String> uploadSalaries( MultipartFile file) throws FileNotFoundException;
}
