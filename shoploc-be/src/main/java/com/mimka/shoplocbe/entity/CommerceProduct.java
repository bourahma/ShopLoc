package com.mimka.shoplocbe.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Commerce_Product")
public class CommerceProduct {

    @EmbeddedId
    private CommerceProductId id;

    @ManyToOne
    @MapsId("commerceId")
    @JoinColumn(name = "commerce_id")
    private Commerce commerce;

    @ManyToOne
    @MapsId("productId")
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;
}

