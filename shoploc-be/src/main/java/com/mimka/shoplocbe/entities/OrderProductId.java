package com.mimka.shoplocbe.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class OrderProductId implements Serializable {
    @Column(name = "order_id")
    private Long orderId;

    @Column(name = "order_product_id")
    private Long productId;
}