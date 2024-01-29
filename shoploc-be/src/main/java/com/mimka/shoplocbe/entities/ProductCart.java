package com.mimka.shoplocbe.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "Product_Cart")
@AllArgsConstructor
@NoArgsConstructor
public class ProductCart {

    @EmbeddedId
    private ProductCartId productCartId;

    @Column(name = "quantity")
    private int quantity;
}
