package com.mimka.shoplocbe.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalTime;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Commerce")
public class Commerce {

    @Id
    @Column(name = "commerce_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "commerce_sequence")
    @SequenceGenerator(name = "commerce_sequence", sequenceName = "commerce_seq", allocationSize = 1, initialValue = 50)
    private Long commerceId;

    @Column(name = "commerce_name", nullable = false)
    private String commerceName;

    @Column(name = "opening_hour", nullable = false)
    private LocalTime openingHour;

    @Column(name = "closing_hour", nullable = false)
    private LocalTime closingHour;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "disabled")
    private boolean disabled;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id", referencedColumnName = "address_id")
    private Address address;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "commerce_type_id", referencedColumnName = "commerce_type_id")
    private CommerceType commerceType;

    @OneToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "commerce_id", referencedColumnName = "commerce_id")
    private List<Product> products;

    @OneToMany(mappedBy = "commerce")
    private Set<ProductCategory> productCategories;

    @OneToMany(mappedBy = "commerce")
    private Set<Merchant> merchants;
}