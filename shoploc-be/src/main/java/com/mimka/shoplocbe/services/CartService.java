package com.mimka.shoplocbe.services;

import com.mimka.shoplocbe.dto.cart.CartDTO;
import com.mimka.shoplocbe.dto.cart.ProductCartDTO;
import com.mimka.shoplocbe.entities.Cart;
import com.mimka.shoplocbe.entities.User;

import java.util.List;

public interface CartService {
    List<Cart> getAllCart (User user);
    CartDTO createCart (User user, CartDTO cartDTO);
    List<CartDTO> getCustomerCarts (User user);
    CartDTO addProductToCart (User user, ProductCartDTO productCartDTO);
    CartDTO removeProductFromCart (User user, int commerceId, int productId);
    CartDTO updateProductInCart (User user, ProductCartDTO productCartDTO);
    void cleanUpCart (User user);
}
