package com.Younes43.GestionRessourcesHumains.Repositories.DemandeSanctionRepositories;

import com.Younes43.GestionRessourcesHumains.Entities.Demande_Sanction.DemandeDeSanction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DemandeDeSanctionRepository extends JpaRepository<DemandeDeSanction,Long> {
    Optional<DemandeDeSanction> findById(Long id);

}
