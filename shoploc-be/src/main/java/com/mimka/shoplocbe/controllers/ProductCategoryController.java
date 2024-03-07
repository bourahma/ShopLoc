package com.mimka.shoplocbe.controllers;

import com.mimka.shoplocbe.dto.product.ProductCategoryDTO;
import com.mimka.shoplocbe.exception.CommerceNotFoundException;
import com.mimka.shoplocbe.exception.ProductCategoryNotFoundException;
import com.mimka.shoplocbe.facades.ProductFacade;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@CrossOrigin(origins = "${allowed.origin}")
@RestController
@RequestMapping("/product")
public class ProductCategoryController {

    private final ProductFacade productFacade;

    @Autowired
    public ProductCategoryController(ProductFacade productFacade) {
        this.productFacade = productFacade;
    }

    @GetMapping("/categories/{commerceId}")
    public Set<ProductCategoryDTO> getCommerceProductCategories (@PathVariable Long commerceId) throws CommerceNotFoundException {
        return this.productFacade.getCommerceProductCategories(commerceId);
    }

    @GetMapping("/category/{productCategoryId}")
    public ProductCategoryDTO getProductCategory (@PathVariable Long productCategoryId) throws ProductCategoryNotFoundException {
        return this.productFacade.getProductCategory(productCategoryId);
    }

    @PostMapping("/category/{commerceId}")
    @ResponseStatus(HttpStatus.CREATED)
    public ProductCategoryDTO addProductCategory (@PathVariable Long commerceId, @RequestBody @Valid ProductCategoryDTO productCategoryDTO) throws CommerceNotFoundException {
        return this.productFacade.createProductCategory(commerceId, productCategoryDTO);
    }

    @PutMapping("/category/{productCategoryId}")
    public ProductCategoryDTO updateProductCategory (@PathVariable Long productCategoryId, @RequestBody @Valid ProductCategoryDTO productCategoryDTO) throws ProductCategoryNotFoundException, CommerceNotFoundException {
        return this.productFacade.updateProductCategory(productCategoryId, productCategoryDTO);
    }
}
