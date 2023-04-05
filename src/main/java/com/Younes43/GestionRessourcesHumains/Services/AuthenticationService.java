package com.Younes43.GestionRessourcesHumains.Services;

import com.Younes43.GestionRessourcesHumains.Entities.Requests.AuthenticationRequest;
import com.Younes43.GestionRessourcesHumains.Entities.Responses.AuthenticationResponse;
import com.Younes43.GestionRessourcesHumains.IServices.IAuthenticationService;
import com.Younes43.GestionRessourcesHumains.Repositories.UserRepository;
import com.Younes43.GestionRessourcesHumains.Security.JWTService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService implements IAuthenticationService {
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final JWTService jwtService;

    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        var user=userRepository.findByEmail(request.getEmail()).orElseThrow();
        var jwtToken=jwtService.generateToken(user);
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(),request.getPassword()));

        return AuthenticationResponse.builder().token(jwtToken).username(user.getUsername())
                .userRole(user.getRole().name()).matricule(user.getMatricule())
                .site(user.getSite()).department(user.getDepartment()).build();
    }
}
