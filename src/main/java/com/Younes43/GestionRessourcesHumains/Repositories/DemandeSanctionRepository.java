package com.Younes43.GestionRessourcesHumains.Repositories;

import com.Younes43.GestionRessourcesHumains.Entities.DemandeSanction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DemandeSanctionRepository extends JpaRepository<DemandeSanction,Long> {
}
