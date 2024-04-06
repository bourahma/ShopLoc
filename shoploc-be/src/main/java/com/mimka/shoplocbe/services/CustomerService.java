package com.mimka.shoplocbe.services;

import com.mimka.shoplocbe.dto.user.CustomerDTO;
import com.mimka.shoplocbe.entities.Customer;
import com.mimka.shoplocbe.entities.FidelityCard;
import com.mimka.shoplocbe.exception.RegistrationException;
import com.mimka.shoplocbe.exception.RegistrationTokenInvalidException;

import java.util.List;

public interface CustomerService {

    Customer getCustomerByUsername (String username);

    List<Customer> getCustomers ( );

    Customer createCustomer(CustomerDTO customerDTO, FidelityCard fidelityCard) throws RegistrationException;

    Customer enableCustomer (String uuid) throws RegistrationTokenInvalidException;

}
