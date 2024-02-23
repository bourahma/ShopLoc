package com.mimka.shoplocbe.facades;

import com.mimka.shoplocbe.dto.commerce.CommerceDTO;
import com.mimka.shoplocbe.dto.product.ProductDTO;
import com.mimka.shoplocbe.exception.CommerceException;

import java.util.List;

public interface CommerceFacade {

    CommerceDTO getCommerce (Long commerceId) throws CommerceException;

    CommerceDTO addCommerce (CommerceDTO commerceDTO);

    List<ProductDTO> getCommerceProducts (Long commerceId) throws CommerceException;

    CommerceDTO addProduct (Long commerceId, ProductDTO productDTO) throws CommerceException;

    List<CommerceDTO> getCommerces ();

    List<CommerceDTO> getCommerceByTypes (String commerceType);

    void deleteCommerce (Long commerceId);

    CommerceDTO updateCommerce (CommerceDTO commerceDTO);
}
