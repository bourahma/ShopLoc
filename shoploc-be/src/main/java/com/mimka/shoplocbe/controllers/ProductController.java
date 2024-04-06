package com.mimka.shoplocbe.controllers;

import com.mimka.shoplocbe.dto.commerce.CommerceDTO;
import com.mimka.shoplocbe.dto.product.ProductDTO;
import com.mimka.shoplocbe.exception.CommerceNotFoundException;
import com.mimka.shoplocbe.exception.ProductException;
import com.mimka.shoplocbe.facades.CommerceFacade;
import com.mimka.shoplocbe.facades.ProductFacade;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@CrossOrigin(origins = "${allowed.origin}")
@RestController
@RequestMapping("/product")
public class ProductController {

    private final ProductFacade productFacade;

    private final CommerceFacade commerceFacade;

    @Autowired
    public ProductController(ProductFacade productFacade,
                             CommerceFacade commerceFacade) {
        this.productFacade = productFacade;
        this.commerceFacade = commerceFacade;
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

    @PostMapping(path = "/commerce/{commerceId}", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyAuthority('SCOPE_MERCHANT')")
    public CommerceDTO addProduct (@PathVariable("commerceId") Long commerceId,
                                   @RequestPart @Valid ProductDTO productDTO,
                                   @RequestParam("multipartFile") MultipartFile multipartFile) throws CommerceNotFoundException {
        return this.commerceFacade.addProduct (commerceId, productDTO, multipartFile);
    }
}
