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
@Table(name = "Commerce_Type")
public class CommerceType {

    @Id
    @Column(name = "commerce_type_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "commerce_type_sequence")
    @SequenceGenerator(name = "commerce_type_sequence", sequenceName = "commerce_type_seq", allocationSize = 1, initialValue = 100)
    private Long commerceTypeId;

    @Column(name = "label", nullable = false)
    private String label;

    @Column(name = "description")
    private String description;
}