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
}
