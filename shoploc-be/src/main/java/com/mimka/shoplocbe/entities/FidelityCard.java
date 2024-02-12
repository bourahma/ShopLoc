package com.mimka.shoplocbe.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Fidelity_Card")
public class FidelityCard {

    @Id
    @Column(name = "fidelity_card_id")
    private String fidelityCardId;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "customer_id", referencedColumnName = "id", nullable = false)
    private Customer customer;

    @Column(name = "balance")
    private double balance;

    @Column(name = "points")
    private double points;

    @OneToMany(mappedBy = "fidelityCard")
    private Set<BalanceTransaction> balanceTransactions;

    @OneToMany(mappedBy = "fidelityCard")
    private Set<PointTransaction> pointTransactions;
}
