package com.mimka.shoplocbe.controllers;

import com.mimka.shoplocbe.dto.product.ProductDTO;
import com.mimka.shoplocbe.exception.ProductException;
import com.mimka.shoplocbe.facades.ProductFacade;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "${allowed.origin}")
@RestController
@RequestMapping("/product")
public class ProductController {

    private final ProductFacade productFacade;

    @Autowired
    public ProductController(ProductFacade productFacade) {
        this.productFacade = productFacade;
    }

    @GetMapping("/detail/{productId}")
    @PreAuthorize("hasAnyAuthority('SCOPE_CUSTOMER', 'SCOPE_MERCHANT', 'SCOPE_ADMINISTRATOR')")
    public ProductDTO productDetails (@PathVariable("productId") Long productId) throws ProductException {
        ProductDTO productDTO =  this.productFacade.getProduct(productId);
        this.productFacade.viewProduct(productId);
        return productDTO;
    }

    @DeleteMapping("/{productId}")
    @PreAuthorize("hasAuthority('SCOPE_MERCHANT')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProduct (@PathVariable("productId") Long productId) {
        this.productFacade.deleteProduct(productId);
    }

    @PutMapping("/")
    @PreAuthorize("hasAuthority('SCOPE_MERCHANT')")
    public ProductDTO updateProduct (@RequestBody @Valid ProductDTO productDTO) throws ProductException {
        return this.productFacade.updateProduct(productDTO);
    }
}
