package com.mimka.shoplocbe.dto.order;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderProductDTO {

    private Long productId;

    private String productName;

    private double price;

    private int quantity;
}
