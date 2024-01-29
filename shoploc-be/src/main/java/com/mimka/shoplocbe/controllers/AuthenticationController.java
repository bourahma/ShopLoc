package com.mimka.shoplocbe.controllers;

import com.mimka.shoplocbe.dto.user.AdministratorDTO;
import com.mimka.shoplocbe.dto.user.AuthDTO;
import com.mimka.shoplocbe.dto.user.CustomerDTO;
import com.mimka.shoplocbe.dto.user.MerchantDTO;
import com.mimka.shoplocbe.services.AuthenticationService;
import com.mimka.shoplocbe.services.AuthenticationServiceImpl;
import com.mimka.shoplocbe.services.RegistrationServiceImpl;
import com.mimka.shoplocbe.services.RegistrationService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/authentication")
@Validated
@Slf4j
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    private final RegistrationService registrationService;

    @Autowired
    public AuthenticationController(AuthenticationServiceImpl authenticationServiceImpl, RegistrationServiceImpl registrationServiceImpl) {
        this.authenticationService = authenticationServiceImpl;
        this.registrationService = registrationServiceImpl;
    }

    @PostMapping("/login")
    public Map<String, String> loginCustomerWithUsername (@RequestBody @Valid AuthDTO authDTO) {
        log.info("Authentication attempt from : {}", authDTO.getUsername());
        return this.authenticationService.loginUserWithUsername(authDTO.getUsername(), authDTO.getPassword());
    }


    @PostMapping( "/customer/register")
    public CustomerDTO registerCustomer (@RequestBody @Valid CustomerDTO customerDTO) throws Exception {
        log.info("Customer registration attempt for : {}", customerDTO.getUsername());
        return this.registrationService.registerCustomer(customerDTO);
    }

    @PostMapping( "/administrator/register")
    public AdministratorDTO registerAdministrator (@RequestBody @Valid AdministratorDTO administratorDTO) throws Exception {
        log.info("Merchant registration attempt for : {}", administratorDTO.getUsername());
        return this.registrationService.registerAdministrator(administratorDTO);
    }

    @PostMapping( "/merchant/register")
    public MerchantDTO registerMerchant (@RequestBody @Valid MerchantDTO merchantDTO) throws Exception {
        log.info("Merchant registration attempt for : {}", merchantDTO.getUsername());
        return this.registrationService.registerMerchant(merchantDTO);
    }

    @GetMapping( "/customer/register/{uuid}")
    public void confirmRegistration (@PathVariable String uuid) {
        log.info("Customer registration confirmation : {}");
        this.registrationService.confirmRegistration(uuid);
    }
}
