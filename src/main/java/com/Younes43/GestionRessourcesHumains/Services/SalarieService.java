package com.Younes43.GestionRessourcesHumains.Services;

import com.Younes43.GestionRessourcesHumains.Entities.Salarie;
import com.Younes43.GestionRessourcesHumains.IServices.ISalarieService;
import com.Younes43.GestionRessourcesHumains.Repositories.SalarieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SalarieService implements ISalarieService {
    DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    private final SalarieRepository salarieRepository;

    @Override
    public Salarie createSalarie(Salarie salarie) {

        salarie.setMatricule(generateID());
        salarie.setDate_dembauche(LocalDate.now().format(dateFormatter));

        return salarieRepository.save(salarie);
    }
    @Override
    public Salarie getSalarie(Long id) {
        Salarie salarie=salarieRepository.findById(id).isPresent()?salarieRepository.findById(id).get():null;

        return salarie;
    }

    @Override
    public Salarie getSalarieBySalarieId(String id) {
        Salarie salarie=salarieRepository.findByMatricule(id).isPresent()?salarieRepository.findByMatricule(id).get():null;
        return salarie;
    }

    @Override
    public Salarie updateSalarie(Salarie salarie) {

        return salarieRepository.save(salarie);
    }
    @Override
    public String deleteSalarie(Long id) {
        salarieRepository.deleteById(id);
        return "deleted successfully";
    }
    @Override
    public List<Salarie> getAllSalarie() {

        return salarieRepository.findAll();

    }
    @Override
    public String createSalaries(List<Salarie> salaries) {
        try{
            for(Salarie salarie:salaries){
                createSalarie(salarie);
            }
        }
        catch(Exception exception){
            exception.printStackTrace();
        }
        return "salaries uploaded successfully";
    }
    public static String generateID() {
        String regex = "^TE\\d{6}$";
        String id = "";
        do {
            UUID uuid = UUID.randomUUID();
            id = "TE" + uuid.toString().substring(0, 6);
        } while (!id.matches(regex));
        return id;
    }
}
