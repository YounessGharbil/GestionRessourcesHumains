package com.Younes43.GestionRessourcesHumains.Repositories;

import com.Younes43.GestionRessourcesHumains.Entities.Salarie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SalarieRepository extends JpaRepository<Salarie,Long> {
    Optional<Salarie> findByMatricule(String matricule);
}
