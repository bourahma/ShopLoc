package com.mimka.shoplocbe.entities;

import jakarta.persistence.*;

import java.util.List;

public class GiftHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gift_history_sequence")
    @SequenceGenerator(name = "gift_history_sequence", sequenceName = "gift_history_seq", allocationSize = 1, initialValue = 1)
    private Long id;

    @Column(name = "date_acquisition")
    private String dateAchat;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "customer_id")
    private List<Customer> customerId;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "product_id")
    private List<Product> giftId;

}
