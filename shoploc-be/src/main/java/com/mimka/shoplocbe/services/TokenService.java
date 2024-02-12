package com.mimka.shoplocbe.services;

import com.mimka.shoplocbe.entities.Customer;
import com.mimka.shoplocbe.entities.Token;

public interface TokenService {

    Token getTokenByCustomer (Customer customer);

    Token createToken (Customer customer);
}
