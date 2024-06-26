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
@Table(name = "Product")
public class Product {

    @Id
    @Column(name = "product_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_sequence")
    @SequenceGenerator(name = "product_sequence", sequenceName = "product_seq", allocationSize = 1, initialValue = 50)
    private Long productId;

    @Column(name = "product_name", nullable = false)
    private String productName;

    @Column(name = "description", columnDefinition = "text")
    private String description;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @Column(name = "price", nullable = false)
    private double price;

    @Column(name = "reward_points_price", nullable = false)
    private double rewardPointsPrice;

    @Column(name = "is_gift", nullable = false)
    private boolean gift;

    @Column(name = "view")
    private Long view;

    @Column(name = "image_url")
    private String imageUrl;

    @ManyToOne
    @JoinColumn(name = "commerce_id", referencedColumnName = "commerce_id")
    private Commerce commerce;

    @ManyToOne
    @JoinColumn(name = "promotion_id", referencedColumnName = "promotion_id")
    private Promotion promotion;

    @ManyToOne
    @JoinColumn(name = "product_category_id", referencedColumnName = "product_category_id")
    private ProductCategory productCategory;

    @Override
    public String toString() {
        return "Product{" +
                "productId=" + productId +
                ", productName='" + productName + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", quantity=" + quantity +
                '}';
    }
}
