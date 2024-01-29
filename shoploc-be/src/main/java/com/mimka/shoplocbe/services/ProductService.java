package com.mimka.shoplocbe.services;

import com.mimka.shoplocbe.dto.product.ProductDTO;

import java.util.List;

public interface ProductService {

    List<ProductDTO> getProductsByCommerce(Long commerceId);
}
