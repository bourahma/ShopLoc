package com.mimka.shoplocbe.controllers;

import com.mimka.shoplocbe.dto.user.CustomerDTO;
import com.mimka.shoplocbe.dto.user.MerchantDTO;
import com.mimka.shoplocbe.entities.Customer;
import com.mimka.shoplocbe.entities.Merchant;
import com.mimka.shoplocbe.facades.CustomerFacade;
import com.mimka.shoplocbe.facades.MerchantFacade;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/authentication")
@Validated
@Slf4j
public class RegistrationController {

    private final CustomerFacade customerFacade;

    private final MerchantFacade merchantFacade;

    public RegistrationController(CustomerFacade customerFacade, MerchantFacade merchantFacade) {
        this.customerFacade = customerFacade;
        this.merchantFacade = merchantFacade;
    }

    @PostMapping( "/merchant/register")
    public Merchant registerMerchant (@RequestBody @Valid MerchantDTO merchantDTO) throws Exception {
        log.info("Merchant registration attempt for : {}", merchantDTO.getUsername());
        return this.merchantFacade.createMerchant(merchantDTO);
    }

    @PostMapping( "/customer/register")
    public Customer registerCustomer (@RequestBody @Valid CustomerDTO customerDTO) throws Exception {
        log.info("Customer registration attempt for : {}", customerDTO.getUsername());
        return this.customerFacade.registerCustomer(customerDTO);
    }

       /* @PostMapping( "/administrator/register")
    public AdministratorDTO registerAdministrator (@RequestBody @Valid AdministratorDTO administratorDTO) throws Exception {
        log.info("Merchant registration attempt for : {}", administratorDTO.getUsername());
        return this.registrationService.registerAdministrator(administratorDTO);
    }*/

    @GetMapping( "/customer/register/{uuid}")
    public void confirmRegistration (@PathVariable String uuid) {
        log.info("Customer registration confirmation : {}");
        this.customerFacade.confirmCustomerRegistration(uuid);
    }
}
