package com.mimka.shoplocbe.services;

import com.mimka.shoplocbe.dto.commerce.CommerceDTO;
import com.mimka.shoplocbe.entities.Commerce;
import com.mimka.shoplocbe.entities.CommerceType;
import com.mimka.shoplocbe.entities.Product;
import com.mimka.shoplocbe.exception.CommerceNotFoundException;

import java.util.List;

public interface CommerceService {

    List<Commerce> getCommerces ();

    Commerce getCommerce(Long id) throws CommerceNotFoundException;

    List<Commerce> getCommercesByType(CommerceType commerceType) throws CommerceNotFoundException;

    Commerce saveCommerce (Commerce commerce);

    Commerce addProduct (Product product, Long commerceId) throws CommerceNotFoundException;

    void disableCommerce (Long commerceId);

    Commerce updateCommerce (CommerceDTO commerceDTO) throws CommerceNotFoundException;
}
