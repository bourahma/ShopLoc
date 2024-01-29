package com.mimka.shoplocbe.services;

import com.mimka.shoplocbe.dto.cart.CartDTO;
import com.mimka.shoplocbe.dto.cart.ProductCartDTO;
import com.mimka.shoplocbe.entities.User;

import java.util.List;

public interface ProductCartService {

    List<ProductCartDTO> getCustomerProducts (User user);

    CartDTO addProductToCart (User user, ProductCartDTO productCartDTO);
}
