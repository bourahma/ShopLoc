package com.mimka.shoplocbe.services;

import com.mimka.shoplocbe.dto.user.MerchantDTO;
import com.mimka.shoplocbe.entities.Merchant;
import com.mimka.shoplocbe.exception.RegistrationException;

public interface MerchantService {

    Merchant getMerchantByUsername (String username);

    Merchant createMerchant(MerchantDTO merchantDTO) throws RegistrationException;
    
}
