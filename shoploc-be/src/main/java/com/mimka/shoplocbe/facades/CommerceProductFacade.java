package com.mimka.shoplocbe.facades;

import com.mimka.shoplocbe.dto.product.ProductDTO;
import com.mimka.shoplocbe.dto.product.ProductDTOUtil;
import com.mimka.shoplocbe.entities.Product;
import com.mimka.shoplocbe.services.ProductService;
import org.springframework.stereotype.Component;

@Component
public class CommerceProductFacade {

    private final ProductService productService;

    private final ProductDTOUtil productDTOUtil;

    public CommerceProductFacade(ProductService productService, ProductDTOUtil productDTOUtil) {
        this.productService = productService;
        this.productDTOUtil = productDTOUtil;
    }


    public ProductDTO addProductToCommerce(ProductDTO productDTO) {
        Product product = this.productService.createProduct(productDTO);
        return null;
    }
}
