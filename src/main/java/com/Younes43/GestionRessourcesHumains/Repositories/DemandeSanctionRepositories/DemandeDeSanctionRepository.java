package com.Younes43.GestionRessourcesHumains.Repositories.DemandeSanctionRepositories;

import com.Younes43.GestionRessourcesHumains.Entities.Demande_Sanction.DemandeDeSanction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DemandeDeSanctionRepository extends JpaRepository<DemandeDeSanction,Long> {
    @Query("SELECT d FROM DemandeDeSanction d WHERE d.demandeStatus <> 'En_Traitement'")
    List<DemandeDeSanction> findAllNotEnTraitement();
}
// List<DemandeDeSanction> findAllByDemandeStatusNotLike("En_Traitement");