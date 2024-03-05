package com.mimka.shoplocbe.facades;

import com.mimka.shoplocbe.dto.product.ProductCategoryDTO;
import com.mimka.shoplocbe.dto.product.ProductDTO;
import com.mimka.shoplocbe.exception.CommerceNotFoundException;
import com.mimka.shoplocbe.exception.ProductCategoryNotFoundException;
import com.mimka.shoplocbe.exception.ProductException;
import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import java.util.Set;

public interface ProductFacade {

    ProductDTO getProduct (Long productId) throws ProductException;

    void deleteProduct (Long productId);

    ProductDTO updateProduct (ProductDTO productDTO) throws ProductException;

    void viewProduct(Long productId) throws ProductException;

    Set<ProductCategoryDTO> getCommerceProductCategories (Long commerceId) throws CommerceNotFoundException;

    ProductCategoryDTO getProductCategory (Long productCategoryId) throws ProductCategoryNotFoundException;

    ProductCategoryDTO createProductCategory (Long commerceId, ProductCategoryDTO productCategoryDTO) throws CommerceNotFoundException;

    ProductCategoryDTO updateProductCategory (Long productCategoryId, ProductCategoryDTO productCategoryDTO) throws CommerceNotFoundException;
}
