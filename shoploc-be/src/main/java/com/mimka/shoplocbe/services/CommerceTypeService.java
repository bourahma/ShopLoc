package com.mimka.shoplocbe.services;

import com.mimka.shoplocbe.dto.commerce.CommerceTypeDTO;
import com.mimka.shoplocbe.entities.CommerceType;
import com.mimka.shoplocbe.exception.CommerceTypeNotFoundException;

import java.util.List;

public interface CommerceTypeService {

    List<CommerceType> getCommerceTypes ();

    CommerceType getCommerceTypeById (Long commerceTypeId) throws CommerceTypeNotFoundException;

    CommerceType createCommerceType (CommerceTypeDTO commerceTypeDTO);
}
