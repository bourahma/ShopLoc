package com.mimka.shoplocbe.service;

import java.util.Map;

public interface AuthenticationService {
    public Map<String, String> loginUserWithUsername (String username, String password);
}
