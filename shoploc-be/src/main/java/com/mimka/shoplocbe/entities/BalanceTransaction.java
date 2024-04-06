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
@Table(name = "Balance_Transaction")
public class BalanceTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "balance_transaction_sequence")
    @SequenceGenerator(name = "balance_transaction_sequence", sequenceName = "balance_transaction_seq", allocationSize = 1, initialValue = 50)
    private Long balanceTransactionId;

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
