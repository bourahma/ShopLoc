package com.mimka.shoplocbe.facades;

import com.mimka.shoplocbe.dto.commerce.CommerceDTO;
import com.mimka.shoplocbe.dto.product.ProductDTO;

import java.util.List;

public interface CommerceFacade {

    CommerceDTO getCommerce (Long commerceId);

    CommerceDTO addCommerce (CommerceDTO commerceDTO);

    List<ProductDTO> getCommerceProducts (Long commerceId);

    CommerceDTO addProduct (Long commerceId, ProductDTO productDTO);

    List<CommerceDTO> getCommerces ();

    List<CommerceDTO> getCommerceByTypes (String commerceType);

    void deleteCommerce (Long commerceId);

    CommerceDTO updateCommerce (CommerceDTO commerceDTO);
}
