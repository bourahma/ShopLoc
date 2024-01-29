package com.mimka.shoplocbe.services;

import com.mimka.shoplocbe.dto.cart.CartDTO;
import com.mimka.shoplocbe.dto.cart.ProductCartDTO;
import com.mimka.shoplocbe.entities.User;
import com.mimka.shoplocbe.repositories.ProductCartRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductCartServiceImpl implements ProductCartService {

    private final ProductCartRepository productCartRepository;

    public ProductCartServiceImpl(ProductCartRepository productCartRepository) {
        this.productCartRepository = productCartRepository;
    }

    @Override
    public List<ProductCartDTO> getCustomerProducts(User user) {
        return null;
    }

    @Override
    public CartDTO addProductToCart(User user, ProductCartDTO productCartDTO) {
        return null;
    }
}
