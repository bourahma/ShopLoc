package com.mimka.shoplocbe.entities;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;

public class ViewedProduct {

    @Id
    @GeneratedValue(generator = "uuid")
    @JoinColumn(name = "product_id")
    private Product id;

    @Column(name = "view_count")
    private int viewCount;
}
