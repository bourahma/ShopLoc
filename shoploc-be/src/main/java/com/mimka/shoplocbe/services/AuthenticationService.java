package com.mimka.shoplocbe.services;

import java.util.Map;

public interface AuthenticationService {

    Map<String, String> loginCustomerWithUsername(String username, String password);

    Map<String, String> loginAdministratorWithUsername(String username, String password);

    Map<String, String> loginMerchantWithUsername(String username, String password);
}
