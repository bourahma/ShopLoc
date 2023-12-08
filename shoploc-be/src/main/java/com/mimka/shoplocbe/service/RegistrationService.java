package com.mimka.shoplocbe.service;

import com.mimka.shoplocbe.dto.user.RegisterDTO;
import com.mimka.shoplocbe.exception.EmailAlreadyUsedException;
import com.mimka.shoplocbe.exception.HandleMailSendException;
import jakarta.mail.MessagingException;

import java.io.UnsupportedEncodingException;

public interface RegistrationService {

    public void register (RegisterDTO registerDTO) throws Exception;

    public boolean verify (String uuid);
}
