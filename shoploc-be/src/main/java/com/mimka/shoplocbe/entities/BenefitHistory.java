package com.mimka.shoplocbe.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class BenefitHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "benefit_history_sequence")
    @SequenceGenerator(name = "benefit_history_sequence", sequenceName = "benefit_history_seq", allocationSize = 1, initialValue = 50)
    private Long id;

    @Column(name = "date_acquisition")
    private String dateAcquisition;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "customer_id")
    private List<Customer> customerId;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "benefit_id")
    private List<Benefit> benefitId;


}
