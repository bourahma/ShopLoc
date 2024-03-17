package com.mimka.shoplocbe.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Vfp_History")
public class VfpHistory {

    @Id
    @Column(name = "vfp_history_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "vfp_history_sequence")
    @SequenceGenerator(name = "vfp_history_sequence", sequenceName = "vfp_history_seq", allocationSize = 1, initialValue = 50)
    private Long giftHistoryId;

    @Column(name = "granted_date")
    private LocalDate grantedDate;

    @Column(name = "expiration_date")
    private LocalDate expirationDate;

    @ManyToOne
    @JoinColumn(name = "customer_id", referencedColumnName = "id", nullable = false)
    private Customer customer;
}
