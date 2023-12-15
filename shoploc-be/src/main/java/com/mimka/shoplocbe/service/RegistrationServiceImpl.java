package com.mimka.shoplocbe.service;

import com.mimka.shoplocbe.dto.user.RegisterDTO;
import com.mimka.shoplocbe.dto.user.UserDTOUtil;
import com.mimka.shoplocbe.exception.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class RegistrationServiceImpl implements RegistrationService {

    private UserServiceImpl userServiceImpl;

    private PasswordEncoder passwordEncoder;

    private UserDTOUtil userDTOUtil;

    @Autowired
    public RegistrationServiceImpl (UserServiceImpl userServiceImpl, UserDTOUtil userDTOUtil, PasswordEncoder passwordEncoder) {
        this.userServiceImpl = userServiceImpl;
        this.userDTOUtil = userDTOUtil;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void register(RegisterDTO registerDTO) throws RegistrationException {
        this.userDTOUtil.checkPasswords(registerDTO);
        String encodedPassword = passwordEncoder.encode(registerDTO.getPassword());
        registerDTO.setPassword(encodedPassword);
        registerDTO.setConfirmedPassword(encodedPassword);
        this.userServiceImpl.createUser(registerDTO);
    }
}
