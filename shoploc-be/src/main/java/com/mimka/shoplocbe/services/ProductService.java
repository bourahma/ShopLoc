package com.mimka.shoplocbe.services;

import com.mimka.shoplocbe.dto.product.ProductDTO;
import com.mimka.shoplocbe.entities.Product;
import com.mimka.shoplocbe.exception.ProductException;

public interface ProductService {

    Product getProduct (Long productId) throws ProductException;

    Product createProduct (ProductDTO productDTO);

    Product updateProduct (ProductDTO productDTO) throws ProductException;

    void deleteProduct (Long productId);

    void viewProduct (Long productId) throws ProductException;
}
