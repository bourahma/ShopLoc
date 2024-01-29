package com.mimka.shoplocbe.services;

import com.mimka.shoplocbe.dto.user.AdministratorDTO;
import com.mimka.shoplocbe.dto.user.CustomerDTO;
import com.mimka.shoplocbe.dto.user.MerchantDTO;
import com.mimka.shoplocbe.dto.user.RegisterDTO;
import com.mimka.shoplocbe.entities.Administrator;
import com.mimka.shoplocbe.entities.Customer;
import com.mimka.shoplocbe.entities.Merchant;
import com.mimka.shoplocbe.entities.User;
import com.mimka.shoplocbe.exception.RegistrationException;
import org.springframework.stereotype.Service;
@Service
public interface UserService {
    User getUserByUsername (String username);
    Administrator createAdministrator(AdministratorDTO administratorDTO) throws RegistrationException;
    Merchant createMerchant(MerchantDTO merchantDTO) throws RegistrationException;
    Customer createCustomer(CustomerDTO customerDTO) throws RegistrationException;
}
