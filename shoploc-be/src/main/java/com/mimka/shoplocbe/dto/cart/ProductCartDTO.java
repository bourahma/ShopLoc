package com.mimka.shoplocbe.dto.cart;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class ProductCartDTO {

    private Long productId;

    private String productName;

    private double price;

    private int quantity;

}

