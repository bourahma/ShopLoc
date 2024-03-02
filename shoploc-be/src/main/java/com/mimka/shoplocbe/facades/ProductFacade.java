package com.mimka.shoplocbe.facades;

import com.mimka.shoplocbe.dto.product.ProductDTO;
import com.mimka.shoplocbe.exception.ProductException;

public interface ProductFacade {

    ProductDTO getProduct (Long productId) throws ProductException;

    void deleteProduct (Long productId);

    ProductDTO updateProduct (ProductDTO productDTO) throws ProductException;

    void viewProduct(Long productId) throws ProductException;
}
