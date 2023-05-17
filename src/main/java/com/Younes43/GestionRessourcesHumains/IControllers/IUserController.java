package com.Younes43.GestionRessourcesHumains.IControllers;


import com.Younes43.GestionRessourcesHumains.Entities.ApplicationUser;
import com.Younes43.GestionRessourcesHumains.Entities.Requests.CreateUserRequest;
import com.Younes43.GestionRessourcesHumains.Entities.Requests.UpdateUserRequest;
import org.springframework.http.ResponseEntity;

import java.util.List;


public interface IUserController {

     ResponseEntity<ApplicationUser> createUser(CreateUserRequest user);


     ResponseEntity<ApplicationUser> getUser( Long id);
     
     // ResponseEntity<ApplicationUser> getUser( String id);

     ResponseEntity<ApplicationUser> updateUser( UpdateUserRequest user);

     ResponseEntity<String> deleteUser( Long id);


     ResponseEntity<List<ApplicationUser>> getAllUser();


}
