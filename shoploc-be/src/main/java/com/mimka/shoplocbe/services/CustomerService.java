package com.mimka.shoplocbe.services;

import com.mimka.shoplocbe.dto.user.CustomerDTO;
import com.mimka.shoplocbe.entities.Customer;
import com.mimka.shoplocbe.entities.FidelityCard;
import com.mimka.shoplocbe.exception.RegistrationException;

public interface CustomerService {

    Customer getCustomerByUsername (String username);

    Customer createCustomer(CustomerDTO customerDTO, FidelityCard fidelityCard) throws RegistrationException;

    Customer enableCustomer (String uuid);

    boolean orderSettled (double total);

    boolean orderSettled(String customerUsername, double total, boolean usingPoints);
}
