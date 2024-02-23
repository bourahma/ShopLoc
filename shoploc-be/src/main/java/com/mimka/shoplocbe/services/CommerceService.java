package com.mimka.shoplocbe.services;

import com.mimka.shoplocbe.dto.commerce.CommerceDTO;
import com.mimka.shoplocbe.entities.Commerce;
import com.mimka.shoplocbe.entities.Product;
import com.mimka.shoplocbe.exception.CommerceException;

import java.util.List;

public interface CommerceService {

    List<Commerce> getCommerces ();

    Commerce getCommerce(Long id) throws CommerceException;

    Commerce createCommerce (CommerceDTO commerceDTO);

    Commerce addProduct (Product product, Long commerceId) throws CommerceException;

    void deleteCommerce (Long commerceId);

    Commerce updateCommerce (CommerceDTO commerceDTO) throws CommerceException;
}
