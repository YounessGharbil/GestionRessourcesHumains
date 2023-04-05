package com.Younes43.GestionRessourcesHumains.IServices;

import javax.mail.MessagingException;
import java.io.IOException;
import java.security.GeneralSecurityException;

public interface IGmailService {
    void sendMail(String to ,String subject,String message) throws GeneralSecurityException, IOException, MessagingException;
}
