package com.mimka.shoplocbe.emailing;


import com.mimka.shoplocbe.entity.User;
import jakarta.mail.MessagingException;

public interface MailService {
    void sendWelcomeEmail(User user) throws MessagingException;
}

