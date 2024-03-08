package com.mimka.shoplocbe.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class GiftHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gift_history_sequence")
    @SequenceGenerator(name = "gift_history_sequence", sequenceName = "gift_history_seq", allocationSize = 1, initialValue = 50)
    private Long id;

    @Column(name = "date_acquisition")
    private String acquisitionDate;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "customer_id")
    private List<Customer> customerId;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "product_id")
    private List<Product> products;

}
