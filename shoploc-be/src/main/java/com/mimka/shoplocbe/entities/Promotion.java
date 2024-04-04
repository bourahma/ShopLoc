package com.mimka.shoplocbe.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "Promotion")
@NoArgsConstructor
public class Promotion {

    @Id
    @Column(name = "promotion_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "promotion_sequence")
    @SequenceGenerator(name = "promotion_sequence", sequenceName = "promotion_seq", allocationSize = 1, initialValue = 50)
    private Long promotionId;

    @Column(name = "label", nullable = false)
    private String label;

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "type", nullable = false)
    private String promotionType;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "commerce_id", referencedColumnName = "commerce_id")
    private Commerce commerce;

    @OneToMany(mappedBy = "promotion")
    private List<Product> products;

    @Column(name = "discount_percent")
    private Integer discountPercent;

    @Column(name = "required_items")
    private Integer requiredItems;

    @Column(name = "offered_items")
    private Integer offeredItems;
    
}