package com.mimka.shoplocbe.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*")
@RestController
public class HelloController {
    @PreAuthorize("hasRole('SCOPE_ADMINISTRATOR')")
    @GetMapping("/admin")
    public String helloAdmin (){
        return "hello admin";
    }

    @PreAuthorize("hasRole('SCOPE_MERCHANT')")
    @GetMapping("/merchant")
    public String helloMerchant() {
        return "Hello Merchant";
    }

    @PreAuthorize("hasRole('SCOPE_CUSTOMER')")
    @GetMapping("/customer")
    public String helloCustomer() {
        return "Hello Customer";
    }

    @GetMapping("/profile")
    public Authentication authenticationProfile (Authentication authentication) {
        return authentication;
    }
}
