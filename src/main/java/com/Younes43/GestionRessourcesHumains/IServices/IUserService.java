package com.Younes43.GestionRessourcesHumains.IServices;

import com.Younes43.GestionRessourcesHumains.Entities.ApplicationUser;


import java.util.List;

public interface IUserService {
    ApplicationUser createUser(ApplicationUser user);
    ApplicationUser getUser(Long id);

    ApplicationUser getUser(String matricule);

    ApplicationUser updateUser(ApplicationUser user);
    String deleteUser(Long id);
    List<ApplicationUser> getAllUser();
}
