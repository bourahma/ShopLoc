package com.mimka.shoplocbe.controllers;

import com.mimka.shoplocbe.dto.commerce.CommerceDTO;
import com.mimka.shoplocbe.dto.commerce.CommerceTypeDTO;
import com.mimka.shoplocbe.dto.product.ProductDTO;
import com.mimka.shoplocbe.exception.CommerceNotFoundException;
import com.mimka.shoplocbe.exception.CommerceTypeNotFoundException;
import com.mimka.shoplocbe.facades.CommerceFacade;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public List<CommerceDTO> getCommerces ( ) {
        return this.commerceFacade.getCommerces();
    }

    @GetMapping("/type/{commerceTypeId}")
    public List<CommerceDTO> getCommercesByType (@PathVariable Long commerceTypeId) throws CommerceNotFoundException, CommerceTypeNotFoundException {
        return this.commerceFacade.getCommercesByType (commerceTypeId);
    }

    @PostMapping("/type")
    @ResponseStatus(HttpStatus.CREATED)
    public CommerceTypeDTO getCommercesByType (@Valid @Validated CommerceTypeDTO commerceTypeDTO) {
        return this.commerceFacade.createCommerceType(commerceTypeDTO);
    }

    @GetMapping("/types")
    public List<CommerceTypeDTO> getCommerceTypes ( ) {
        return this.commerceFacade.getCommerceTypes();
    }

    @GetMapping("/{commerceId}")
    public CommerceDTO getCommerce (@PathVariable("commerceId") Long commerceId) throws CommerceNotFoundException {
        return this.commerceFacade.getCommerce (commerceId);
    }

    @PostMapping("/{commerceId}")
    @ResponseStatus(HttpStatus.CREATED)
    public CommerceDTO addProduct (@PathVariable("commerceId") Long commerceId, @RequestBody @Valid ProductDTO productDTO) throws CommerceNotFoundException {
        return this.commerceFacade.addProduct (commerceId, productDTO);
    }

    @GetMapping("/{commerceId}/products")
    public List<ProductDTO> getCommerceProducts (@PathVariable("commerceId") Long commerceId) throws CommerceNotFoundException {
        return this.commerceFacade.getCommerceProducts(commerceId);
    }

    @PutMapping("/{commerceId}")
    public CommerceDTO updateCommerce (@PathVariable("commerceId") Long commerceId, @RequestBody @Valid CommerceDTO commerceDTO) throws CommerceNotFoundException, CommerceTypeNotFoundException {
        commerceDTO.setCommerceId(commerceId);
        return this.commerceFacade.updateCommerce(commerceDTO);
    }

    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    public CommerceDTO createCommerce (@RequestBody @Valid CommerceDTO commerceDTO) throws CommerceTypeNotFoundException {
        return this.commerceFacade.addCommerce(commerceDTO);
    }

    @DeleteMapping("/{commerceId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void disableCommerce (@PathVariable("commerceId") Long commerceId) {
        this.commerceFacade.disableCommerce(commerceId);
    }
    // TODO : RF - Commerce is disabled, not deleted.
}