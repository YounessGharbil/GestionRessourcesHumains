package com.Younes43.GestionRessourcesHumains.Repositories.DemandeSanctionRepositories;

import com.Younes43.GestionRessourcesHumains.Entities.Demande_Sanction.RAPPORT_TEAM_LEADER;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RapportTeamLeaderRepository extends JpaRepository<RAPPORT_TEAM_LEADER,Long> {
    Optional<RAPPORT_TEAM_LEADER> findByDemandeDeSanctionId(Long id);
}
