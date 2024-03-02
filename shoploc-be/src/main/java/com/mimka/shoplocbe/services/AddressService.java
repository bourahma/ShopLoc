package com.mimka.shoplocbe.services;

import com.mimka.shoplocbe.dto.commerce.AddressDTO;
import com.mimka.shoplocbe.entities.Address;

public interface AddressService {

    Address createAddress (AddressDTO addressDTO);
}
