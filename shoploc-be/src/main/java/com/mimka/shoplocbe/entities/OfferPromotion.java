package com.mimka.shoplocbe.entities;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import lombok.Getter;
import lombok.Setter;

@DiscriminatorValue("OFFER")
@Getter
@Setter
public class OfferPromotion extends Promotion {
    @Column(name = "required_items", nullable = false)
    private int requiredItems;

    @Column(name = "offered_items", nullable = false)
    private int offeredItems;
}
