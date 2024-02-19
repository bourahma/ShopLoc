package com.mimka.shoplocbe.facades;

import com.mimka.shoplocbe.dto.product.ProductDTO;

import java.util.List;

public interface ProductFacade {

    ProductDTO getProduct (Long productId);

    void deleteProduct (Long productId);

    ProductDTO updateProduct (ProductDTO productDTO);

    void viewProduct(Long productId);
}
