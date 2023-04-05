package com.Younes43.GestionRessourcesHumains.Repositories;


import com.Younes43.GestionRessourcesHumains.Entities.ApplicationUser;
import com.Younes43.GestionRessourcesHumains.Entities.Enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<ApplicationUser,Long> {
    Optional<ApplicationUser>  findByEmail(String email);
    Optional<ApplicationUser>  findByMatricule(String matricule);

    Optional<ApplicationUser> findByRoleAndDepartmentAndSite(Role role,String department,String site);



    //@Query(name = "ApplicationUser.findByUserSuperior")
    //Optional<ApplicationUser>  findByUserSuperior( @Param("matricule") String matricule);

}
