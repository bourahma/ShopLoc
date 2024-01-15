package com.mimka.shoplocbe.service;

import com.mimka.shoplocbe.dto.commerce.CommerceDTO;
import com.mimka.shoplocbe.entity.Commerce;

import java.util.List;

public interface CommerceService {

    public List<CommerceDTO> getCommerces ();

    Commerce getCommerce(Long id);
}
