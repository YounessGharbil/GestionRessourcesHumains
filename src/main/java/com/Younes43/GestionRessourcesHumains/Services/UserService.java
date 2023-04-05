package com.Younes43.GestionRessourcesHumains.Services;

import com.Younes43.GestionRessourcesHumains.Entities.ApplicationUser;
import com.Younes43.GestionRessourcesHumains.IServices.IUserService;
import com.Younes43.GestionRessourcesHumains.Repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
@RequiredArgsConstructor
public class UserService implements IUserService {


    private final UserRepository userRepository;
    @Override
    public ApplicationUser createUser(ApplicationUser user) {
        return userRepository.save(user);
    }

    @Override
    public ApplicationUser getUser(Long id) {
        ApplicationUser user=userRepository.findById(id).isPresent()?userRepository.findById(id).get():null;
        return user;
    }
    @Override
    public ApplicationUser getUser(String matricule) {
        ApplicationUser user=userRepository.findByMatricule(matricule).isPresent()?
                userRepository.findByMatricule(matricule).get():null;
        return user;
    }

    @Override
    public ApplicationUser updateUser(ApplicationUser user) {
        return userRepository.save(user);
    }

    @Override
    public String deleteUser(Long id) {
        userRepository.deleteById(id);
        return "deleted successfully";
    }

    @Override
    public List<ApplicationUser> getAllUser() {
        return userRepository.findAll();
    }
}
