package com.mimka.shoplocbe.controller;

import com.mimka.shoplocbe.dto.user.AuthDTO;
import com.mimka.shoplocbe.dto.user.RegisterDTO;
import com.mimka.shoplocbe.exception.EmailAlreadyUsedException;
import com.mimka.shoplocbe.exception.HandleMailSendException;
import com.mimka.shoplocbe.service.AuthenticationServiceImpl;
import com.mimka.shoplocbe.service.RegistrationServiceImpl;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/authentication")
@Slf4j
public class AuthenticationController {

    @Autowired
    private AuthenticationServiceImpl authenticationServiceImpl;

    @Autowired
    private RegistrationServiceImpl registrationServiceImpl;

    @GetMapping("/profile")
    public Authentication authenticationProfile (Authentication authentication) {
        return authentication;
    }

    @PostMapping("/login")
    public Map<String, String> loginUserWithUsername (@RequestBody @Valid AuthDTO authDTO) {
        log.info("Authentication attempt from : {}", authDTO.getUsername());
        return this.authenticationServiceImpl.loginUserWithUsername(authDTO.getUsername(), authDTO.getPassword());
    }

    @PostMapping( "/register")
    @ResponseStatus(value = HttpStatus.CREATED)
    public void registerUser (@RequestBody @Valid RegisterDTO registerDTO) throws Exception {
        log.info("Registration attempt for : {}", registerDTO.getUsername());
        this.registrationServiceImpl.register(registerDTO);
    }

    @GetMapping("/verify/{uuid}")
    public void verifyUserRegistration (@PathVariable String uuid) {
        log.info("Registration validation attempt");
        this.registrationServiceImpl.verify(uuid);
    }
}
