package com.mimka.shoplocbe.controllers;

import com.mimka.shoplocbe.dto.commerce.CommerceDTO;
import com.mimka.shoplocbe.dto.commerce.CommerceTypeDTO;
import com.mimka.shoplocbe.dto.product.ProductCategoryDTO;
import com.mimka.shoplocbe.dto.product.ProductDTO;
import com.mimka.shoplocbe.exception.CommerceNotFoundException;
import com.mimka.shoplocbe.exception.CommerceTypeNotFoundException;
import com.mimka.shoplocbe.facades.CommerceFacade;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@CrossOrigin(origins = "${allowed.origin}")
@RestController
@RequestMapping("/commerce")
public class CommerceController {

    private final CommerceFacade commerceFacade;

    @Autowired
    public CommerceController(CommerceFacade commerceFacade) {
        this.commerceFacade = commerceFacade;
    }

    @GetMapping("/")
    @PreAuthorize("hasAnyAuthority('SCOPE_CUSTOMER', 'SCOPE_MERCHANT', 'SCOPE_ADMINISTRATOR')")
    public List<CommerceDTO> commerces ( ) {
        return this.commerceFacade.getCommerces();
    }

    @GetMapping("/type/{commerceTypeId}")
    @PreAuthorize("hasAnyAuthority('SCOPE_CUSTOMER')")
    public List<CommerceDTO> commercesByType (@PathVariable Long commerceTypeId) throws CommerceNotFoundException, CommerceTypeNotFoundException {
        return this.commerceFacade.getCommercesByType (commerceTypeId);
    }

    @PostMapping("/type")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyAuthority('SCOPE_MERCHANT')")
    public CommerceTypeDTO createCommerceByType (@Valid @Validated CommerceTypeDTO commerceTypeDTO) {
        return this.commerceFacade.createCommerceType(commerceTypeDTO);
    }

    @GetMapping("/types")
    @PreAuthorize("hasAnyAuthority('SCOPE_CUSTOMER', 'SCOPE_MERCHANT', 'SCOPE_ADMINISTRATOR')")
    public List<CommerceTypeDTO> commerceTypes ( ) {
        return this.commerceFacade.getCommerceTypes();
    }

    @GetMapping("/categories/{commerceId}")
    @PreAuthorize("hasAnyAuthority('SCOPE_CUSTOMER', 'SCOPE_MERCHANT', 'SCOPE_ADMINISTRATOR')")
    public Set<ProductCategoryDTO> commerceProductCategories (@PathVariable Long commerceId) throws CommerceNotFoundException {
        return this.commerceFacade.getCommerceProductCategories(commerceId);
    }

    @GetMapping("/{commerceId}")
    @PreAuthorize("hasAnyAuthority('SCOPE_CUSTOMER', 'SCOPE_MERCHANT', 'SCOPE_ADMINISTRATOR')")
    public CommerceDTO commerce (@PathVariable("commerceId") Long commerceId) throws CommerceNotFoundException {
        return this.commerceFacade.getCommerce (commerceId);
    }

    @PostMapping("/{commerceId}")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyAuthority('SCOPE_MERCHANT')")
    public CommerceDTO addProduct (@PathVariable("commerceId") Long commerceId, @RequestBody @Valid ProductDTO productDTO) throws CommerceNotFoundException {
        return this.commerceFacade.addProduct (commerceId, productDTO);
    }

    @GetMapping("/{commerceId}/products")
    @PreAuthorize("hasAnyAuthority('SCOPE_CUSTOMER', 'SCOPE_MERCHANT', 'SCOPE_ADMINISTRATOR')")
    public List<ProductDTO> commerceProducts (@PathVariable("commerceId") Long commerceId) throws CommerceNotFoundException {
        return this.commerceFacade.getCommerceProducts(commerceId);
    }

    @PutMapping("/{commerceId}")
    @PreAuthorize("hasAnyAuthority('SCOPE_MERCHANT')")
    public CommerceDTO updateCommerce (@PathVariable("commerceId") Long commerceId, @RequestBody @Valid CommerceDTO commerceDTO) throws CommerceNotFoundException, CommerceTypeNotFoundException {
        commerceDTO.setCommerceId(commerceId);
        return this.commerceFacade.updateCommerce(commerceDTO);
    }

    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyAuthority('SCOPE_ADMINISTRATOR')")
    public CommerceDTO createCommerce (@RequestBody @Valid CommerceDTO commerceDTO) throws CommerceTypeNotFoundException {
        return this.commerceFacade.addCommerce(commerceDTO);
    }

    @DeleteMapping("/{commerceId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAnyAuthority('SCOPE_MERCHANT', 'SCOPE_ADMINISTRATOR')")
    public void disableCommerce (@PathVariable("commerceId") Long commerceId) {
        this.commerceFacade.disableCommerce(commerceId);
    }
}