package com.mimka.shoplocbe.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "Cart")
public class Cart {

    @Id
    @Column(name = "cart_id")
    private Long cartId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "cart_commerce_id")
    private Commerce commerce;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "cart_customer_id")
    private User customer;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<ProductCart> productCart;

}
