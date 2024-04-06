package com.mimka.shoplocbe.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Merchant")
@Setter
@Getter
public class Merchant extends User {

    @ManyToOne
    @JoinColumn(name = "commerce_id", referencedColumnName = "commerce_id")
    private Commerce commerce;

    @Column(name = "subscription_date")
    private LocalDate subscriptionDate;
}