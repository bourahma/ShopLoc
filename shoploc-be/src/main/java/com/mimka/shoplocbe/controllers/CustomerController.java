package com.mimka.shoplocbe.controllers;

import com.mimka.shoplocbe.dto.user.CustomerDTO;
import com.mimka.shoplocbe.facades.CustomerFacade;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@CrossOrigin(origins = "${allowed.origin}")
@RestController
@Validated
@Slf4j
public class CustomerController {

    private final CustomerFacade customerFacade;

    @Autowired
    public CustomerController(CustomerFacade customerFacade) {
        this.customerFacade = customerFacade;
    }

    @GetMapping("/customer/profile")
    public CustomerDTO customerProfile (Principal principal) {
        return this.customerFacade.getCustomer(principal.getName());
    }
}
