package com.mimka.shoplocbe.controller;

import com.mimka.shoplocbe.dto.product.ProductDTO;
import com.mimka.shoplocbe.service.ProductServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {

    private ProductServiceImpl productServiceImpl;
    @Autowired
    public ProductController(ProductServiceImpl productServiceImpl) {
        this.productServiceImpl = productServiceImpl;
    }

    @GetMapping("/{commerceId}")
    @ResponseStatus(value = HttpStatus.OK)
    public List<ProductDTO> productsByCommerce(@PathVariable("commerceId") Long commerceId){
        return productServiceImpl.getProductsByCommerce(commerceId);
    }
}
