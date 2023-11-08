package com.mimka.shoplocbe.controller;

import com.mimka.shoplocbe.dto.user.UserDTO;
import com.mimka.shoplocbe.exception.EmailAlreadyUsedException;
import com.mimka.shoplocbe.service.AuthenticationServiceImpl;
import com.mimka.shoplocbe.service.RegistrationServiceImpl;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/authentication")
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
    public Map<String, String> loginUserWithUsername (@RequestBody UserDTO userDTO) {
        return this.authenticationServiceImpl.loginUserWithUsername(userDTO.getUsername(), userDTO.getPassword());
    }

    @PostMapping( "/register")
    @ResponseStatus(value = HttpStatus.CREATED)
    public void registerUser (@RequestBody UserDTO userDTO) throws MessagingException, EmailAlreadyUsedException {
        this.registrationServiceImpl.register(userDTO);
    }

    @GetMapping("/verify/{uuid}")
    public void verifyUserRegistration (@PathVariable String uuid) {
        this.registrationServiceImpl.verify(uuid);
    }
}
