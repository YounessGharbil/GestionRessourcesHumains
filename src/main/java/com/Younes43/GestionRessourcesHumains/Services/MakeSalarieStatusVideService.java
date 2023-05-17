package com.Younes43.GestionRessourcesHumains.Services;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.Younes43.GestionRessourcesHumains.Entities.Salarie;
import com.Younes43.GestionRessourcesHumains.Entities.Enums.SalarieStatus;
import com.Younes43.GestionRessourcesHumains.Repositories.SalarieRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MakeSalarieStatusVideService {
    private final SalarieRepository salarieRepository;
    
    DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    @Scheduled(cron = "0 */2 * ? * *")
    public void ClearSalarieStatus()  {
        List<Salarie> salaries=salarieRepository.findAll();
        for (Salarie salarie:salaries) {
            try {
                LocalDate  dateEmbauche = LocalDate.parse(salarie.getDate_dembauche(), dateFormatter);
                LocalDate currentDate = LocalDate.now();
                
                Period period = Period.between(dateEmbauche, currentDate);
              
                boolean isOneYearDifference = period.getYears() == 0 && period.getMonths() == 0 && period.getDays() > 1;
                if (isOneYearDifference && !salarie.getStatus().equals(SalarieStatus.VIDE.name())) {
                    salarie.setStatus(SalarieStatus.VIDE.name());
                    salarieRepository.save(salarie);
                }

            } catch (Exception e) {
                System.out.println("hError:"+e.getMessage());
            }
               
        }
       
    }
       

      


    }
    

