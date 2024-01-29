package com.mimka.shoplocbe.services;

import com.mimka.shoplocbe.dto.cart.CartDTO;
import com.mimka.shoplocbe.dto.cart.ProductCartDTO;
import com.mimka.shoplocbe.entities.Cart;
import com.mimka.shoplocbe.entities.User;
import com.mimka.shoplocbe.repositories.CartRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;

    private final ProductCartService productCartService;

    public CartServiceImpl(CartRepository cartRepository, ProductCartService productCartService) {
        this.cartRepository = cartRepository;
        this.productCartService = productCartService;
    }

    @Override
    public List<Cart> getAllCart(User user) {
        System.out.println("toto");
        return this.cartRepository.findAll();
    }

    @Override
    public CartDTO createCart(User user, CartDTO cartDTO) {
        return null;
    }

    @Override
    public List<CartDTO> getCustomerCarts(User user) {
        return null;
    }

    @Override
    public CartDTO addProductToCart(User user, ProductCartDTO productCartDTO) {
        return null;
    }

    @Override
    public CartDTO removeProductFromCart(User user, int commerceId, int productId) {
        return null;
    }

    @Override
    public CartDTO updateProductInCart(User user, ProductCartDTO productCartDTO) {
        return null;
    }

    @Override
    public void cleanUpCart(User user) {

    }
}
