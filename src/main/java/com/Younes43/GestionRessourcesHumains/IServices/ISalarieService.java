package com.Younes43.GestionRessourcesHumains.IServices;

import com.Younes43.GestionRessourcesHumains.Entities.Salarie;

import java.util.List;

public interface ISalarieService {
    Salarie createSalarie(Salarie salarie);
    Salarie getSalarie(Long id);

    Salarie getSalarieBySalarieId(String id);

    Salarie updateSalarie(Salarie salarie);
    String deleteSalarie(Long id);
    List<Salarie> getAllSalarie();
    String createSalaries(List<Salarie> salaries);
}
