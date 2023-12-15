package com.mimka.shoplocbe.service;

import com.mimka.shoplocbe.dto.product.ProductDTO;

import java.util.List;

public interface ProductService {

    public List<ProductDTO> getProductsByCommerce(Long commerceId);
}
