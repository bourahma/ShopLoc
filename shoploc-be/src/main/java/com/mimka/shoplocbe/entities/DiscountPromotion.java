package com.mimka.shoplocbe.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@DiscriminatorValue("DISCOUNT")
@Getter
@Setter
@NoArgsConstructor
@Entity
public class DiscountPromotion extends Promotion {

    @Column(name = "discount_percent", nullable = false)
    private Integer discountPercent;
}