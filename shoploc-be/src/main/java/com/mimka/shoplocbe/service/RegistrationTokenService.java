package com.mimka.shoplocbe.service;

import com.mimka.shoplocbe.entity.User;

public interface RegistrationTokenService {

    public String setUserVerificationToke (User user);

    public String resetVerificationToken (String uuid, User user);

    public void deleteVerificationToken (String uuid);
}
