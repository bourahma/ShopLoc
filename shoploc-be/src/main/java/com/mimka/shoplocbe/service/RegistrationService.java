package com.mimka.shoplocbe.service;

import com.mimka.shoplocbe.dto.user.RegisterDTO;
public interface RegistrationService {

    public void register (RegisterDTO registerDTO) throws Exception;
}
