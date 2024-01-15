package com.mimka.shoplocbe.controller;

import com.mimka.shoplocbe.dto.commerce.CommerceDTO;
import com.mimka.shoplocbe.dto.product.ProductDTO;
import com.mimka.shoplocbe.emailing.MailService;
import com.mimka.shoplocbe.emailing.MailServiceImpl;
import com.mimka.shoplocbe.entity.Commerce;
import com.mimka.shoplocbe.service.*;
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

    private final MailService mailService;

    private final UserService userService;

    @Autowired
    public CommerceController(UserServiceImpl userServiceImpl, CommerceServiceImpl commerceServiceImpl, ProductServiceImpl productServiceImpl, MailServiceImpl mailServiceImpl) {
        this.commerceService = commerceServiceImpl;
        this.productService = productServiceImpl;
        this.mailService = mailServiceImpl;
        this.userService = userServiceImpl;
    }

    @GetMapping("/")
    @ResponseStatus(value = HttpStatus.OK)
    public List<CommerceDTO> commerces (Principal principal) {
        try {
            this.mailService.sendWelcomeEmail(this.userService.getUserByUsername(principal.getName()));
        } catch (Exception exception) {
            System.out.println("error");
        }
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
