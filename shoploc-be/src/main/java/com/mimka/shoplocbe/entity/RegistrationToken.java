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
    @Column(name = "token_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "token_sequence")
    @SequenceGenerator(name = "token_sequence", sequenceName = "token_seq", allocationSize = 1, initialValue = 1)
    private Long userId;

    @Column(name = "uuid", nullable = false, unique = true)
    private String uuid;

    @OneToOne(orphanRemoval = false)
    @JoinColumn(name = "utilisateur_id", referencedColumnName = "utilisateur_id", nullable = false, unique = true)
    private User user;
}
