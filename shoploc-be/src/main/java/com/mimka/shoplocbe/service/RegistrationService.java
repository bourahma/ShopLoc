package com.mimka.shoplocbe.service;


import com.mimka.shoplocbe.dto.user.UserDTO;
import com.mimka.shoplocbe.exception.EmailAlreadyUsedException;
import jakarta.mail.MessagingException;

import java.io.UnsupportedEncodingException;

public interface RegistrationService {

    public void register (UserDTO userDTO) throws EmailAlreadyUsedException, MessagingException, UnsupportedEncodingException;

    public boolean verify (String uuid);
}
