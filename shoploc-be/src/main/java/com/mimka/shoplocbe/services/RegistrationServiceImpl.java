package com.mimka.shoplocbe.services;

import com.mimka.shoplocbe.dto.DtoUtil;
import com.mimka.shoplocbe.dto.user.*;
import com.mimka.shoplocbe.entities.Customer;
import com.mimka.shoplocbe.entities.FidelityCard;
import com.mimka.shoplocbe.entities.Merchant;
import com.mimka.shoplocbe.exception.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class RegistrationServiceImpl implements RegistrationService {

    private final CustomerService customerService;

    private final MerchantService merchantService;

    private final AdministratorService administratorService;

    private final FidelityCardService fidelityCardService;

    private PasswordEncoder passwordEncoder;

    private final DtoUtil dtoUtil;

    @Autowired
    public RegistrationServiceImpl (CustomerService createCustomer, MerchantService merchantService, AdministratorService administratorService, FidelityCardService fidelityCardService, PasswordEncoder passwordEncoder, DtoUtil dtoUtil) {
        this.customerService = createCustomer;
        this.merchantService = merchantService;
        this.administratorService = administratorService;
        this.fidelityCardService = fidelityCardService;
        this.passwordEncoder = passwordEncoder;
        this.dtoUtil = dtoUtil;
    }

    @Override
    public Customer registerCustomer(CustomerDTO customerDTO) throws RegistrationException {
        // Check passwords are valid.
        if (this.dtoUtil.checkPasswords(customerDTO.getConfirmedPassword(), customerDTO.getPassword())) {
            // Encode password.
            String encodedPassword = passwordEncoder.encode(customerDTO.getPassword());
            customerDTO.setPassword(encodedPassword);
            customerDTO.setConfirmedPassword(encodedPassword);
        }
        FidelityCard fidelityCard = this.fidelityCardService.createFidelityCard();
        //Create customer.
        return this.customerService.createCustomer(customerDTO, fidelityCard);
    }

    @Override
    public MerchantDTO registerMerchant(MerchantDTO merchantDTO) throws RegistrationException {
        String password = merchantDTO.getPassword();

        // Check passwords are valid.
        if (this.dtoUtil.checkPasswords(merchantDTO.getConfirmedPassword(), merchantDTO.getPassword())) {
            // Encode password.
            String encodedPassword = passwordEncoder.encode(merchantDTO.getPassword());
            merchantDTO.setPassword(encodedPassword);
            merchantDTO.setConfirmedPassword(encodedPassword);
        }
        //Create merchant.
        Merchant merchant = this.merchantService.createMerchant(merchantDTO);
        merchantDTO.setPassword(password);

        return this.dtoUtil.toMerchantDTO(merchant);
    }
    @Override
    public AdministratorDTO registerAdministrator(AdministratorDTO administratorDTO) throws RegistrationException {
        // Check passwords are valid.
        if (this.dtoUtil.checkPasswords(administratorDTO.getConfirmedPassword(), administratorDTO.getPassword())) {
            // Encode password.
            String encodedPassword = passwordEncoder.encode(administratorDTO.getPassword());
            administratorDTO.setPassword(encodedPassword);
            administratorDTO.setConfirmedPassword(encodedPassword);
        }
        //Create customer.
        return this.dtoUtil.toAdministratorDTO(this.administratorService.createAdministrator(administratorDTO));
    }

    @Override
    public Customer confirmRegistration(String uuid) {
        return this.customerService.enableCustomer(uuid);
    }
}
