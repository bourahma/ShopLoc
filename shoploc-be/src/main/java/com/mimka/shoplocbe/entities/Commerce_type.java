package com.mimka.shoplocbe.entities;

import jakarta.persistence.*;

public class Commerce_type {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "commerce_type_sequence")
    private Long id;

    @Column(name = "label", nullable = false)
    private String label;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Commerce commerceId;
}