package com.mimka.shoplocbe.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalTime;

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
    @SequenceGenerator(name = "commerce_sequence", sequenceName = "commerce_seq", allocationSize = 1, initialValue = 1)
    private Long commerceId;

    @Column(name = "commerce_name", nullable = false)
    private String commerceName;

    @Column(name = "opening_hour", nullable = false)
    private LocalTime openingHour;

    @Column(name = "closing_hour", nullable = false)
    private LocalTime closingHour;
}
