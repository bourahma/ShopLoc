package com.mimka.shoplocbe.facades;

import com.mimka.shoplocbe.dto.product.ProductDTO;
import com.mimka.shoplocbe.dto.product.ProductDTOUtil;
import com.mimka.shoplocbe.entities.Product;
import com.mimka.shoplocbe.exception.ProductException;
import com.mimka.shoplocbe.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
@Component
public class ProductFacadeImpl implements ProductFacade {

    private final ProductService productService;

    private final ProductDTOUtil productDTOUtil;

    @Autowired
    public ProductFacadeImpl(ProductService productService, ProductDTOUtil productDTOUtil) {
        this.productService = productService;
        this.productDTOUtil = productDTOUtil;
    }

    @Override
    public ProductDTO getProduct(Long productId) throws ProductException {
        Product product = this.productService.getProduct(productId);

        return this.productDTOUtil.toProductDTO(product);
    }

    @Override
    public void deleteProduct(Long productId) {
        this.productService.deleteProduct(productId);
    }

    @Override
    public ProductDTO updateProduct(ProductDTO productDTO) throws ProductException {
        Product product = this.productService.updateProduct(productDTO);

        return this.productDTOUtil.toProductDTO(product);
    }

    @Override
    public void viewProduct(Long productId) throws ProductException {
        this.productService.viewProduct(productId);
    }
}
