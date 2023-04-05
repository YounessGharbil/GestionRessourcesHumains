package com.Younes43.GestionRessourcesHumains.Repositories.DemandeSanctionRepositories;

import com.Younes43.GestionRessourcesHumains.Entities.Demande_Sanction.RAPPORT_RH;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RapportRhRepository extends JpaRepository<RAPPORT_RH,Long> {
    Optional<RAPPORT_RH> findByDemandeDeSanctionId(Long id);

}
