package com.mimka.shoplocbe.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "Promotion_History")
public class PromotionHistory {

    @Id
    @Column(name = "promotion_history_id")
    private Long promotionHistoryId;

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;

    @Column(name = "description", nullable = false)
    private String description;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "commerce_id", referencedColumnName = "commerce_id")
    private Commerce commerce;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(name = "discount_percent", nullable = false)
    private Integer discountPercent;

    @Column(name = "required_items", nullable = false)
    private int requiredItems;

    @Column(name = "offered_items", nullable = false)
    private int offeredItems;

}
