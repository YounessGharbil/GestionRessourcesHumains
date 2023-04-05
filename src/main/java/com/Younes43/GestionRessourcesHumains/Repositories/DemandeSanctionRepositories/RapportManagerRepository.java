package com.Younes43.GestionRessourcesHumains.Repositories.DemandeSanctionRepositories;

import com.Younes43.GestionRessourcesHumains.Entities.Demande_Sanction.RAPPORT_MANAGER;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository

public interface RapportManagerRepository extends JpaRepository<RAPPORT_MANAGER,Long> {
    Optional<RAPPORT_MANAGER> findByDemandeDeSanctionId(Long id);
}
