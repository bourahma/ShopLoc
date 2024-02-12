package com.mimka.shoplocbe.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Point_Transaction")
public class PointTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "point_transaction_sequence")
    @SequenceGenerator(name = "point_transaction_sequence", sequenceName = "point_transaction_seq", allocationSize = 1, initialValue = 10)
    @Column(name = "point_transaction_id")
    private Long pointTransactionId;

    @Column(name = "transaction_date", nullable = false)
    private LocalDateTime transactionDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private TransactionType type;

    @Column(name = "amount", nullable = false)
    private double amount;

    @ManyToOne
    @JoinColumn(name = "commerce_id", nullable = false)
    private Commerce commerce;

    @ManyToOne
    @JoinColumn(name = "fidelity_card_id", nullable = false)
    private FidelityCard fidelityCard;
}
