package com.mimka.shoplocbe.facades;

import com.mimka.shoplocbe.dto.commerce.CommerceDTO;
import com.mimka.shoplocbe.dto.commerce.CommerceTypeDTO;
import com.mimka.shoplocbe.dto.product.ProductDTO;
import com.mimka.shoplocbe.exception.CommerceNotFoundException;
import com.mimka.shoplocbe.exception.CommerceTypeNotFoundException;

import java.util.List;

public interface CommerceFacade {

    CommerceDTO getCommerce (Long commerceId) throws CommerceNotFoundException;

    CommerceDTO addCommerce (CommerceDTO commerceDTO) throws CommerceTypeNotFoundException;

    List<ProductDTO> getCommerceProducts (Long commerceId) throws CommerceNotFoundException;

    CommerceDTO addProduct (Long commerceId, ProductDTO productDTO) throws CommerceNotFoundException;

    List<CommerceDTO> getCommerces ();

    List<CommerceDTO> getCommercesByType(Long commerceTypeId) throws CommerceTypeNotFoundException, CommerceNotFoundException;

    void disableCommerce (Long commerceId);

    List<CommerceTypeDTO> getCommerceTypes ();

    CommerceTypeDTO createCommerceType (CommerceTypeDTO commerceTypeDTO);

    CommerceDTO updateCommerce (CommerceDTO commerceDTO) throws CommerceNotFoundException, CommerceTypeNotFoundException;
}
