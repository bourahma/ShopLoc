package com.mimka.shoplocbe.controllers;

import com.mimka.shoplocbe.dto.user.AuthDTO;
import com.mimka.shoplocbe.services.AuthenticationService;
import com.mimka.shoplocbe.services.AuthenticationServiceImpl;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@CrossOrigin(origins = "${allowed.origin}")
@RestController
@RequestMapping("/authentication")
@Validated
@Slf4j
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @Autowired
    public AuthenticationController(AuthenticationServiceImpl authenticationServiceImpl) {
        this.authenticationService = authenticationServiceImpl;
    }

    @PostMapping("/customer/login")
    public Map<String, String> loginCustomerWithUsername (@RequestBody @Valid AuthDTO authDTO) {
        log.info("Customer authentication attempt from : {}", authDTO.getUsername());
        return this.authenticationService.loginCustomerWithUsername(authDTO.getUsername(), authDTO.getPassword());
    }

    @PostMapping("/administrator/login")
    public Map<String, String> loginAdministratorWithUsername (@RequestBody @Valid AuthDTO authDTO) {
        log.info("Administrator authentication attempt from : {}", authDTO.getUsername());
        return this.authenticationService.loginAdministratorWithUsername(authDTO.getUsername(), authDTO.getPassword());
    }

    @PostMapping("/merchant/login")
    public Map<String, String> loginMerchantWithUsername (@RequestBody @Valid AuthDTO authDTO) {
        log.info("Merchant authentication attempt from : {}", authDTO.getUsername());
        return this.authenticationService.loginMerchantWithUsername(authDTO.getUsername(), authDTO.getPassword());
    }
}
