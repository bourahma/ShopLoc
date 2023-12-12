package com.mimka.shoplocbe.service;

import com.mimka.shoplocbe.dto.product.ProductDTO;
import com.mimka.shoplocbe.dto.product.ProductDTOUtil;
import com.mimka.shoplocbe.repository.CommerceProductRepository;
import com.mimka.shoplocbe.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl {

    private ProductRepository productRepository;

    private CommerceProductRepository commerceProductRepository;

    private ProductDTOUtil productDTOUtil;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, CommerceProductRepository commerceProductRepository, ProductDTOUtil productDTOUtil) {
        this.productRepository = productRepository;
        this.commerceProductRepository = commerceProductRepository;
        this.productDTOUtil = productDTOUtil;
    }

    public List<ProductDTO> getProductsByCommerce(Long commerceId) {
        return (List<ProductDTO>) this.commerceProductRepository.findProductsByCommerceId(commerceId);
    }
}
