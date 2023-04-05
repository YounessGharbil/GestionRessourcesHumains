package com.Younes43.GestionRessourcesHumains.Controllers;
import com.Younes43.GestionRessourcesHumains.Entities.*;
import com.Younes43.GestionRessourcesHumains.Entities.Requests.CreateUserRequest;
import com.Younes43.GestionRessourcesHumains.Entities.Requests.UpdateUserRequest;
import com.Younes43.GestionRessourcesHumains.IControllers.IUserController;
import com.Younes43.GestionRessourcesHumains.IServices.IUserService;
import com.Younes43.GestionRessourcesHumains.Services.SalarieService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor

public class UserController implements IUserController {
    private final IUserService UserService;
    private final PasswordEncoder passwordEncoder;
    private final SalarieService salarieService;



    @PostMapping("/create")
    @Override
    public ResponseEntity<ApplicationUser> createUser(@RequestBody CreateUserRequest createUserRequest) {
        var salarie=salarieService.getSalarieBySalarieId(createUserRequest.getMatricule());
        if(salarie==null){
            return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
        }

        var user=ApplicationUser.builder()
                .email(createUserRequest.getEmail())
                .password(passwordEncoder.encode(createUserRequest.getPassword()))
                .role(createUserRequest.getRole())
                .matricule(createUserRequest.getMatricule())
                .build();


        return new ResponseEntity<>(UserService.createUser(user), HttpStatus.CREATED);
    }
    @GetMapping("/get/{id}")
    @Override
    public ResponseEntity<ApplicationUser> getUser(@PathVariable Long id) {
        ApplicationUser user=UserService.getUser(id);
        if(user==null){
            return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(user,HttpStatus.FOUND);
    }

    @PutMapping("/update")
    @Override
    public ResponseEntity<ApplicationUser> updateUser(@RequestBody UpdateUserRequest userUpdated) {
        ApplicationUser user=UserService.getUser(userUpdated.getId());
        if(user==null){
            return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
        }
        user.setEmail(userUpdated.getEmail());
        user.setPassword(passwordEncoder.encode(userUpdated.getPassword()));
        user.setRole(userUpdated.getRole());
        return new ResponseEntity<>(UserService.updateUser(user),HttpStatus.ACCEPTED);
    }
    @DeleteMapping("/delete/{id}")
    @Override
    public ResponseEntity<String> deleteUser( @PathVariable Long id) {
        ApplicationUser user=UserService.getUser(id);
        if(user==null){
            return new ResponseEntity<>("user does not exist",HttpStatus.NOT_FOUND);
        }
        UserService.deleteUser(id);
        return new ResponseEntity<>("deleted successfully",HttpStatus.ACCEPTED);
    }

    @GetMapping("/getAll")
    @Override
    public ResponseEntity<List<ApplicationUser>> getAllUser() {

        return new ResponseEntity<>(UserService.getAllUser(),HttpStatus.OK);
    }
}
