package com.mimka.shoplocbe.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@DiscriminatorValue("MERCHANT")
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Setter
@Getter
public class Merchant extends User {

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "commerce_id")
    private Commerce commerce;

    @Column(name = "subscription_date")
    private LocalDate subscriptionDate;
}