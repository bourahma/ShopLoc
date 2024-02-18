package com.mimka.shoplocbe.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@DiscriminatorValue("DISCOUNT")
@Getter
@Setter
public class DiscountPromotion extends Promotion {

    @Column(name = "discount-percent", nullable = false)
    private Integer discountPercent;
}