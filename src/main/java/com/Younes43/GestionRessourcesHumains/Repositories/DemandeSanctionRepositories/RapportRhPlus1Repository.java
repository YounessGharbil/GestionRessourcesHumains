package com.Younes43.GestionRessourcesHumains.Repositories.DemandeSanctionRepositories;

import com.Younes43.GestionRessourcesHumains.Entities.Demande_Sanction.DemandeDeSanction;
import com.Younes43.GestionRessourcesHumains.Entities.Demande_Sanction.RAPPORT_RHPLUS1;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RapportRhPlus1Repository extends JpaRepository<RAPPORT_RHPLUS1,Long> {
    Optional<RAPPORT_RHPLUS1> findByDemandeDeSanctionId(Long id);
    Optional<RAPPORT_RHPLUS1> findByDemandeDeSanction(DemandeDeSanction demandeDeSanction);


}
