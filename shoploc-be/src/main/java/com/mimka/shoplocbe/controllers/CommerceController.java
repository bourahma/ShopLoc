package com.mimka.shoplocbe.controllers;

import com.mimka.shoplocbe.api.map.MapAPI;
import com.mimka.shoplocbe.dto.commerce.CommerceDTO;
import com.mimka.shoplocbe.dto.product.ProductDTO;
import com.mimka.shoplocbe.exception.CommerceException;
import com.mimka.shoplocbe.facades.CommerceFacade;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/commerce")
public class CommerceController {

    private final CommerceFacade commerceFacade;

    @Autowired
    public CommerceController(CommerceFacade commerceFacade) {
        this.commerceFacade = commerceFacade;
    }

    @GetMapping("/")
    public List<CommerceDTO> commerces ( ) {
        return this.commerceFacade.getCommerces();
    }

    @GetMapping("/type/{commerceType}")
    public List<CommerceDTO> getCommercesByType (@PathVariable String commerceType) {
        return this.commerceFacade.getCommerceByTypes(commerceType);
    }

    @GetMapping("/{commerceId}")
    public CommerceDTO getCommerce (@PathVariable("commerceId") Long commerceId) throws CommerceException {
        System.out.println(commerceId);
        return this.commerceFacade.getCommerce(commerceId);
    }

    @PostMapping("/{commerceId}")
    public CommerceDTO addProduct (@PathVariable("commerceId") Long commerceId, @RequestBody @Valid ProductDTO productDTO) throws CommerceException {
        return this.commerceFacade.addProduct(commerceId, productDTO);
    }

    @GetMapping("/{commerceId}/products")
    public List<ProductDTO> commerceProducts (@PathVariable("commerceId") Long commerceId) throws CommerceException {
        return this.commerceFacade.getCommerceProducts(commerceId);
    }

    @PutMapping("/{commerceId}")
    public CommerceDTO updateCommerce (@RequestBody @Valid CommerceDTO commerceDTO){
        return this.commerceFacade.updateCommerce(commerceDTO);
    }

    @PostMapping("/")
    public CommerceDTO addCommerce (@RequestBody @Valid CommerceDTO commerceDTO){
        return this.commerceFacade.addCommerce(commerceDTO);
    }

    @DeleteMapping("/{commerceId}")
    public void deleteCommerce (@PathVariable("commerceId") Long commerceId){
        this.commerceFacade.deleteCommerce(commerceId);
    }
}
