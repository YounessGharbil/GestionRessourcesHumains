package com.Younes43.GestionRessourcesHumains.Repositories.DemandeSanctionRepositories;

import com.Younes43.GestionRessourcesHumains.Entities.Demande_Sanction.RAPPORT_SUPERVISEUR;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RapportSuperviseurRepository extends JpaRepository<RAPPORT_SUPERVISEUR,Long> {
    Optional<RAPPORT_SUPERVISEUR> findByDemandeDeSanctionId(Long id);
}
