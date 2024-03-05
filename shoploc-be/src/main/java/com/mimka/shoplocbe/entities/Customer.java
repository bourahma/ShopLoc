package com.mimka.shoplocbe.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "Customer")
public class Customer extends User {

    @Column(name = "is_vfp_membership")
    private boolean isVfpMembership;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "fidelity_card_id", referencedColumnName = "fidelity_card_id", nullable = false)
    private FidelityCard fidelityCard;
}
