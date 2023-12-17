package com.mimka.shoplocbe.controller;

import com.mimka.shoplocbe.dto.commerce.CommerceDTO;
import com.mimka.shoplocbe.dto.product.ProductDTO;
import com.mimka.shoplocbe.service.CommerceServiceImpl;
import com.mimka.shoplocbe.service.ProductServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/commerce")
public class CommerceController {

    private CommerceServiceImpl commerceServiceImpl;

    private ProductServiceImpl productServiceImpl;

    @Autowired
    public CommerceController(CommerceServiceImpl commerceServiceImpl, ProductServiceImpl productServiceImpl) {
        this.commerceServiceImpl = commerceServiceImpl;
        this.productServiceImpl = productServiceImpl;
    }

    @GetMapping("/")
    @ResponseStatus(value = HttpStatus.OK)
    public List<CommerceDTO> commerces () {
        return this.commerceServiceImpl.getCommerces();
    }

    @GetMapping("/{commerceId}")
    @ResponseStatus(value = HttpStatus.OK)
    public CommerceDTO commerce (@PathVariable("commerceId") Long commerceId) {
        return this.commerceServiceImpl.getCommerce(commerceId);
    }

    @GetMapping("/{commerceId}/products")
    @ResponseStatus(value = HttpStatus.OK)
    public List<ProductDTO> commerceProducts (@PathVariable("commerceId") Long commerceId){
        return productServiceImpl.getProductsByCommerce(commerceId);
    }
}
