package com.mimka.shoplocbe.services;

import com.mimka.shoplocbe.dto.product.ProductCategoryDTO;
import com.mimka.shoplocbe.entities.ProductCategory;
import com.mimka.shoplocbe.exception.ProductCategoryNotFoundException;

public interface ProductCategoryService {

    ProductCategory getProductCategory (Long productCategoryId) throws ProductCategoryNotFoundException;

    ProductCategory createProductCategory (ProductCategoryDTO productCategoryDTO);

    ProductCategory saveProductCategory (ProductCategory productCategory);

    ProductCategory updateProductCategory (Long productCategoryId, ProductCategoryDTO productCategoryDTO) throws ProductCategoryNotFoundException;
}
