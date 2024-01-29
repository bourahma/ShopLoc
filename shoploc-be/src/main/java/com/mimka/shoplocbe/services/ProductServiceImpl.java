package com.mimka.shoplocbe.services;

import com.mimka.shoplocbe.dto.product.ProductDTO;
import com.mimka.shoplocbe.repositories.CommerceProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private CommerceProductRepository commerceProductRepository;

    @Autowired
    public ProductServiceImpl(CommerceProductRepository commerceProductRepository) {
        this.commerceProductRepository = commerceProductRepository;
    }

    @Override
    public List<ProductDTO> getProductsByCommerce(Long commerceId) {
        return this.commerceProductRepository.findProductsByCommerceId(commerceId);
    }
}
