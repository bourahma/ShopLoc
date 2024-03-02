package com.mimka.shoplocbe.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "Promotion")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type", discriminatorType = DiscriminatorType.STRING)
@Getter
@Setter
public abstract class Promotion {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "promotion_sequence")
    @SequenceGenerator(name = "promotion_sequence", sequenceName = "promotion_seq", allocationSize = 1, initialValue = 1)
    private Long promotionId;

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;

    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "product_id")
    private Product product;

    @Column(name = "description", nullable = false)
    private String description;
}