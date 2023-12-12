package com.mimka.shoplocbe.service;

import com.mimka.shoplocbe.dto.user.RegisterDTO;
import com.mimka.shoplocbe.dto.user.UserDTOUtil;
import com.mimka.shoplocbe.entity.RegistrationToken;
import com.mimka.shoplocbe.entity.User;
import com.mimka.shoplocbe.exception.*;
import com.mimka.shoplocbe.repository.UserRepository;
import jakarta.mail.MessagingException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class RegistrationServiceImpl implements RegistrationService {

    @Autowired
    private UserServiceImpl userServiceImpl;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RegistrationTokenServiceImpl registrationTokenServiceImpl;

    @Autowired
    private EmailServiceImpl emailServiceImpl;

    @Autowired
    private UserDTOUtil userDTOUtil;

    @Override
    public void register(RegisterDTO registerDTO) throws MessagingException, HandleMailSendException, RegistrationException {
        this.userDTOUtil.checkPasswords(registerDTO);
        String encodedPassword = passwordEncoder.encode(registerDTO.getPassword());
        registerDTO.setPassword(encodedPassword);
        registerDTO.setConfirmedPassword(encodedPassword);
        User user = this.userServiceImpl.createUser(registerDTO);
        this.emailServiceImpl.sendVerificationEmail(user);
    }

    @Override
    @Transactional
    public boolean verify(String uuid) {
        try {
            this.validateRegistration(uuid);
        } catch (Exception e) {
        }
        return  true;
    }

    public void validateRegistration (String uuid) {
        RegistrationToken registrationToken = this.registrationTokenServiceImpl.getByUuid(uuid);
        User user = registrationToken.getUser();
        if (user != null || !user.getEnabled()) {
            this.registrationTokenServiceImpl.deleteVerificationToken(uuid);
            user.setEnabled(true);
            this.userRepository.save(user);
        }
    }
}
