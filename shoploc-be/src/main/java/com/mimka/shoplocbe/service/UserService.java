package com.mimka.shoplocbe.service;

import com.mimka.shoplocbe.dto.user.RegisterDTO;
import com.mimka.shoplocbe.entity.User;
import com.mimka.shoplocbe.exception.RegistrationException;
import jakarta.mail.MessagingException;
import org.springframework.stereotype.Service;
@Service
public interface UserService {

    public User getUserByEmail (String email);

    public User getUserByUsername (String username);

    public User createUser (RegisterDTO registerDTO) throws MessagingException, RegistrationException;
}
