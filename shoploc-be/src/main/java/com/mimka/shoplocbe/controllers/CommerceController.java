package com.mimka.shoplocbe.controllers;

import com.mimka.shoplocbe.dto.commerce.CommerceDTO;
import com.mimka.shoplocbe.dto.product.ProductDTO;
import com.mimka.shoplocbe.services.MailService;
import com.mimka.shoplocbe.services.MailServiceImpl;
import com.mimka.shoplocbe.entities.Commerce;
import com.mimka.shoplocbe.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/commerce")
public class CommerceController {

    private CommerceService commerceService;
    private ProductService productService;
    private final UserService userService;

    @Autowired
    public CommerceController(UserServiceImpl userServiceImpl, CommerceServiceImpl commerceServiceImpl, ProductServiceImpl productServiceImpl) {
        this.commerceService = commerceServiceImpl;
        this.productService = productServiceImpl;
        this.userService = userServiceImpl;
    }

    @GetMapping("/")
    @ResponseStatus(value = HttpStatus.OK)
    public List<CommerceDTO> commerces (Principal principal) {
        return this.commerceService.getCommerces();
    }

    @GetMapping("/{commerceId}")
    @ResponseStatus(value = HttpStatus.OK)
    public Commerce commerce (@PathVariable("commerceId") Long commerceId) {
        return this.commerceService.getCommerce(commerceId);
    }

    @GetMapping("/{commerceId}/products")
    @ResponseStatus(value = HttpStatus.OK)
    public List<ProductDTO> commerceProducts (@PathVariable("commerceId") Long commerceId){
        return productService.getProductsByCommerce(commerceId);
    }
}
