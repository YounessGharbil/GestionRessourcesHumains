package com.Younes43.GestionRessourcesHumains.IServices;

import com.Younes43.GestionRessourcesHumains.Entities.Requests.AuthenticationRequest;
import com.Younes43.GestionRessourcesHumains.Entities.Responses.AuthenticationResponse;

public interface IAuthenticationService {
    AuthenticationResponse authenticate(AuthenticationRequest request);
}
