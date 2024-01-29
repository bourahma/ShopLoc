package com.mimka.shoplocbe.services;

import com.mimka.shoplocbe.dto.user.AdministratorDTO;
import com.mimka.shoplocbe.dto.user.CustomerDTO;
import com.mimka.shoplocbe.dto.user.MerchantDTO;
import com.mimka.shoplocbe.exception.RegistrationException;

public interface RegistrationService {
    CustomerDTO registerCustomer(CustomerDTO customerDTO) throws RegistrationException;
    MerchantDTO registerMerchant(MerchantDTO merchantDTO) throws RegistrationException;
    AdministratorDTO registerAdministrator(AdministratorDTO administratorDTO) throws RegistrationException;
    void confirmRegistration (String uuid);
}
