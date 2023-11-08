package com.mimka.shoplocbe.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "Token")
public class RegistrationToken {

    @Id
    @Column(name = "uuid")
    private String uuid;

    @OneToOne(orphanRemoval = false)
    @JoinColumn(name = "utilisateur_id", referencedColumnName = "utilisateur_id")
    private User user;
}
