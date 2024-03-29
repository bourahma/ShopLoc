package com.mimka.shoplocbe.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class BenefitHistory {

    @Id
    @Column(name = "benefit_history_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "benefit_history_sequence")
    @SequenceGenerator(name = "benefit_history_sequence", sequenceName = "benefit_history_seq", allocationSize = 1, initialValue = 50)
    private Long benefitHistoryId;

    @Column(name = "qr_code")
    private String qrCode;

    @Column(name = "acquisition_date")
    private LocalDate acquisitionDate;

    @Column(name = "acquisition_time")
    private Timestamp acquisitionTime;

    @Column(name = "license_plate_number")
    private String licensePlateNumber;

    @ManyToOne
    @JoinColumn(name = "customer_id", referencedColumnName = "id")
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "benefit_id", referencedColumnName = "benefit_id")
    private Benefit benefit;
}