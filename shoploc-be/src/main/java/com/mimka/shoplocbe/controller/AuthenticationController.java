package com.mimka.shoplocbe.controller;

import com.mimka.shoplocbe.dto.user.AuthDTO;
import com.mimka.shoplocbe.dto.user.RegisterDTO;
import com.mimka.shoplocbe.service.AuthenticationServiceImpl;
import com.mimka.shoplocbe.service.RegistrationServiceImpl;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/authentication")
@Slf4j
public class AuthenticationController {

    private AuthenticationServiceImpl authenticationServiceImpl;

    private RegistrationServiceImpl registrationServiceImpl;

    @Autowired
    public AuthenticationController(AuthenticationServiceImpl authenticationServiceImpl, RegistrationServiceImpl registrationServiceImpl) {
        this.authenticationServiceImpl = authenticationServiceImpl;
        this.registrationServiceImpl = registrationServiceImpl;
    }

    @PostMapping("/login")
    @ResponseStatus(value = HttpStatus.OK)
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
}
