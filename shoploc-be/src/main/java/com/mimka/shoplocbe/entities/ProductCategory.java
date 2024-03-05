package com.mimka.shoplocbe.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Product_Category")
public class ProductCategory {

    @Id
    @Column(name = "product_category_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_category_sequence")
    @SequenceGenerator(name = "product_category_sequence", sequenceName = "product_category_seq", allocationSize = 1, initialValue = 1000)
    private Long productCategoryId;

    @ManyToOne
    @JoinColumn(name = "commerce_id", referencedColumnName = "commerce_id")
    private Commerce commerce;

    @Column(name = "label", nullable = false)
    private String label;

    @Column(name = "description", nullable = false)
    private String description;
}
