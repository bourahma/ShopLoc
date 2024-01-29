package com.mimka.shoplocbe.services;

import java.util.Map;

public interface AuthenticationService {
    Map<String, String> loginUserWithUsername (String username, String password);
}
