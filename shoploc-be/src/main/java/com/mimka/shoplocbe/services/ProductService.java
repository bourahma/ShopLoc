package com.mimka.shoplocbe.services;

import com.mimka.shoplocbe.dto.product.ProductDTO;
import com.mimka.shoplocbe.entities.Product;

import java.util.List;

public interface ProductService {

    List<Product> getProductsByCommerce(Long commerceId);

    Product getProduct (Long productId);

    Product createProduct (ProductDTO productDTO);

    Product updateProduct (ProductDTO productDTO);

    void deleteProduct (Long productId);

    void viewProduct (Long productId);
}
