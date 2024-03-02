package com.mimka.shoplocbe.controllers;

import com.mimka.shoplocbe.dto.user.AdministratorDTO;
import com.mimka.shoplocbe.dto.user.CustomerDTO;
import com.mimka.shoplocbe.dto.user.MerchantDTO;
import com.mimka.shoplocbe.entities.Customer;
import com.mimka.shoplocbe.entities.Merchant;
import com.mimka.shoplocbe.exception.RegistrationException;
import com.mimka.shoplocbe.exception.RegistrationTokenInvalidException;
import com.mimka.shoplocbe.facades.AdministratorFacade;
import com.mimka.shoplocbe.facades.CustomerFacade;
import com.mimka.shoplocbe.facades.MerchantFacade;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "${allowed.origin}")
@RestController
@RequestMapping("/authentication")
@Validated
@Slf4j
public class RegistrationController {

    private final CustomerFacade customerFacade;

    private final MerchantFacade merchantFacade;

    private final AdministratorFacade administratorFacade;

    public RegistrationController(CustomerFacade customerFacade, MerchantFacade merchantFacade, AdministratorFacade administratorFacade) {
        this.customerFacade = customerFacade;
        this.merchantFacade = merchantFacade;
        this.administratorFacade = administratorFacade;
    }

    @PostMapping( "/merchant/register")
    @ResponseStatus(HttpStatus.CREATED)
    public Merchant registerMerchant (@RequestBody @Valid MerchantDTO merchantDTO) throws Exception {
        log.info("Merchant registration attempt for : {}", merchantDTO.getUsername());
        return this.merchantFacade.createMerchant(merchantDTO);
    }

    @PostMapping( "/customer/register")
    @ResponseStatus(HttpStatus.CREATED)
    public Customer registerCustomer (@RequestBody @Valid CustomerDTO customerDTO) throws Exception {
        log.info("Customer registration attempt for : {}", customerDTO.getUsername());
        return this.customerFacade.registerCustomer(customerDTO);
    }

    @PostMapping( "/administrator/register")
    @ResponseStatus(HttpStatus.CREATED)
    public AdministratorDTO registerAdministrator (@RequestBody @Valid AdministratorDTO administratorDTO) throws Exception {
        log.info("Administrator registration attempt for : {}", administratorDTO.getUsername());
        return this.administratorFacade.registerAdministrator(administratorDTO);
    }

    @GetMapping( "/customer/register/{uuid}")
    public void confirmRegistration (@PathVariable String uuid) throws RegistrationTokenInvalidException {
        log.info("Customer registration confirmation : {}");
        this.customerFacade.confirmCustomerRegistration(uuid);
    }
}
