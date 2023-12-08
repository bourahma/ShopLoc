package com.mimka.shoplocbe.service;

import com.mimka.shoplocbe.entity.RegistrationToken;
import com.mimka.shoplocbe.entity.User;
import com.mimka.shoplocbe.repository.RegistrationTokenRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class RegistrationTokenServiceImpl implements RegistrationTokenService {

    @Autowired
    private RegistrationTokenRepository registrationTokenRepository;

    RegistrationToken getByUuid (String uuid) {
        return registrationTokenRepository.findByUuid(uuid);
    }

    @Override
    public String setUserVerificationToke(User user) {
        RegistrationToken registrationToken = new RegistrationToken();

        String uuid = UUID.randomUUID().toString();
        registrationToken.setUuid(uuid);
        registrationToken.setUser(user);

        this.registrationTokenRepository.save(registrationToken);

        return uuid;
    }

    @Transactional
    @Override
    public String resetVerificationToken(User user) {
        String uuid = UUID.randomUUID().toString();
        this.registrationTokenRepository.updateToken(uuid, user);

        return uuid;
    }

    @Override
    public void deleteVerificationToken(String uuid) {
        this.registrationTokenRepository.deleteByUuid(uuid);
    }

}
