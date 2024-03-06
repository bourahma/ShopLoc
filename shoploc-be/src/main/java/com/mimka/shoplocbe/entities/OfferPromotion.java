package com.mimka.shoplocbe.entities;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@DiscriminatorValue("OFFER")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class OfferPromotion extends Promotion {

    @Column(name = "required_items", nullable = false)
    private int requiredItems;

    @Column(name = "offered_items", nullable = false)
    private int offeredItems;
}
