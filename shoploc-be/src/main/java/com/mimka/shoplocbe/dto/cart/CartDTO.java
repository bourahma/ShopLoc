package com.mimka.shoplocbe.dto.cart;

import com.mimka.shoplocbe.entities.ProductCart;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class CartDTO {

    private Long cartId;

    private String commerceName;

    private long commerceId;

    private Set<ProductCart> productCart;
}
