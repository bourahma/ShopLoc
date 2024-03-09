package com.mimka.shoplocbe.services;

import com.mimka.shoplocbe.dto.product.ProductDTO;
import com.mimka.shoplocbe.entities.Commerce;
import com.mimka.shoplocbe.entities.CommerceType;
import com.mimka.shoplocbe.entities.Product;
import com.mimka.shoplocbe.exception.ProductException;

import java.util.List;

public interface ProductService {

    Product getProduct (Long productId) throws ProductException;

    List<Product> getGiftProducts ( );

    List<Product> getCommerceGiftProducts (Commerce commerce);

    List<Product> getGiftProductsPerCommerceType (CommerceType commerceType);

    Product createProduct (ProductDTO productDTO);

    Product updateProduct (ProductDTO productDTO) throws ProductException;

    void deleteProduct (Long productId);

    void viewProduct (Long productId) throws ProductException;
}
