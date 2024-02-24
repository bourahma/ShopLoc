package com.mimka.shoplocbe.facades;

import com.mimka.shoplocbe.dto.commerce.CommerceDTO;
import com.mimka.shoplocbe.dto.product.ProductDTO;
import com.mimka.shoplocbe.exception.CommerceNotFoundException;

import java.util.List;

public interface CommerceFacade {

    CommerceDTO getCommerce (Long commerceId) throws CommerceNotFoundException;

    CommerceDTO addCommerce (CommerceDTO commerceDTO);

    List<ProductDTO> getCommerceProducts (Long commerceId) throws CommerceNotFoundException;

    CommerceDTO addProduct (Long commerceId, ProductDTO productDTO) throws CommerceNotFoundException;

    List<CommerceDTO> getCommerces ();

    List<CommerceDTO> getCommerceByTypes (String commerceType);

    void disableCommerce (Long commerceId);

    CommerceDTO updateCommerce (CommerceDTO commerceDTO) throws CommerceNotFoundException;
}
