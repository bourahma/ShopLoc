package com.mimka.shoplocbe.controllers;

import com.mimka.shoplocbe.dto.cart.CartDTO;
import com.mimka.shoplocbe.dto.cart.ProductCartDTO;
import com.mimka.shoplocbe.entities.Cart;
import com.mimka.shoplocbe.entities.User;
import com.mimka.shoplocbe.services.CartService;
import com.mimka.shoplocbe.services.CartServiceImpl;
import com.mimka.shoplocbe.services.UserService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/cart")
@Validated
@Slf4j
public class CartController {

    private final CartService cartService;

    private final UserService userService;

    public CartController(CartServiceImpl cartServiceImpl, UserService userService) {
        this.cartService = cartServiceImpl;
        this.userService = userService;
    }


    @GetMapping( "/")
    public List<Cart> customerCarts (Principal principal) {
        log.info("Customer cart : get customer cart {}");
        User user = this.userService.getUserByUsername(principal.getName());
        return this.cartService.getAllCart(user);
    }

    @PostMapping("/")
    public CartDTO createCart (Principal principal, @RequestBody @Valid CartDTO cartDTO) {
        log.info("Customer cart : create cart {}");
        User user = this.userService.getUserByUsername(principal.getName());
        return this.cartService.createCart(user, cartDTO);
    }

    @PutMapping( "/product")
    public CartDTO updateProductInCart (Principal principal, @RequestBody @Valid ProductCartDTO productCartDTO) {
        log.info("Customer cart : update product in cart {}");
        User user = this.userService.getUserByUsername(principal.getName());
        return this.cartService.updateProductInCart(user, productCartDTO);
    }

    @PostMapping( "/product")
    public CartDTO addProductToCart (Principal principal, @RequestBody @Valid ProductCartDTO productCartDTO) {
        log.info("Customer cart : add product to cart {}");
        User user = this.userService.getUserByUsername(principal.getName());
        return this.cartService.addProductToCart(user, productCartDTO);
    }

    @DeleteMapping("/")
    public void cleanUpCart (Principal principal) {
        log.info("Customer cart : clean up cart {}");
        User user = this.userService.getUserByUsername(principal.getName());
        this.cartService.cleanUpCart(user);
    }

    @DeleteMapping("/product/{commerceId}/{productId}")
    public CartDTO removeProductFromCart (Principal principal, @PathVariable int commerceId, @PathVariable int productId) {
        log.info("Customer cart : delete product from cart {}");
        User user = this.userService.getUserByUsername(principal.getName());
        return this.cartService.removeProductFromCart(user, commerceId, productId);
    }
}
