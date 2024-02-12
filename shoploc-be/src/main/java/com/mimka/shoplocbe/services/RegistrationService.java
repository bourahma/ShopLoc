package com.mimka.shoplocbe.services;

import com.mimka.shoplocbe.dto.user.AdministratorDTO;
import com.mimka.shoplocbe.dto.user.CustomerDTO;
import com.mimka.shoplocbe.dto.user.MerchantDTO;
import com.mimka.shoplocbe.entities.Customer;
import com.mimka.shoplocbe.exception.RegistrationException;

public interface RegistrationService {
    Customer registerCustomer(CustomerDTO customerDTO) throws RegistrationException;

    MerchantDTO registerMerchant(MerchantDTO merchantDTO) throws RegistrationException;

    AdministratorDTO registerAdministrator(AdministratorDTO administratorDTO) throws RegistrationException;

    Customer confirmRegistration (String uuid);
}
