package com.mimka.shoplocbe.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Gift_History")
public class GiftHistory {

    @Id
    @Column(name = "gift_history_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gift_history_sequence")
    @SequenceGenerator(name = "gift_history_sequence", sequenceName = "gift_history_seq", allocationSize = 1, initialValue = 50)
    private Long giftHistoryId;

    @Column(name = "purchase_date")
    private LocalDate purchaseDate;

    @ManyToOne
    @JoinColumn(name = "customer_id", referencedColumnName = "id", nullable = false)
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "product_id", nullable = false)
    private Product product;

}
