package com.mimka.shoplocbe.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Benefit")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Benefit {

    @Id
    @Column(name = "benefit_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "benefit_sequence")
    @SequenceGenerator(name = "benefit_sequence", sequenceName = "benefit_seq", allocationSize = 1, initialValue = 50)
    private Long benefitId;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "image_url", nullable = false)
    private String imageUrl;

    @Column(name = "benefit_available", nullable = false)
    private boolean benefitAvailable;

    @Column(name = "license_plate_number")
    private String licensePlateNumber;
}