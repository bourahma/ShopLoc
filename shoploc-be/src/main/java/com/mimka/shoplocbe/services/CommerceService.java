package com.mimka.shoplocbe.services;

import com.mimka.shoplocbe.dto.commerce.CommerceDTO;
import com.mimka.shoplocbe.entities.Commerce;

import java.util.List;

public interface CommerceService {

    List<CommerceDTO> getCommerces ();
    Commerce getCommerce(Long id);
}
