package com.mimka.shoplocbe.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Table(name = "Utilisateur")
@Entity
public class User {


    @Id
    @Column(name = "utilisateur_id")
    private Long userId;

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "lastname", nullable = false)
    private String lastname;

    @Column(name = "firstname", nullable = false)
    private String firstname;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "enabled", nullable = false)
    private Boolean enabled;

    @Column(name = "phone_number")
    private String phoneNumber;

    @ManyToOne
    @JoinColumn(name = "role_id", referencedColumnName = "role_id")
    private Role role;
}