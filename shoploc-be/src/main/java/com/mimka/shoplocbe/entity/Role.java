package com.mimka.shoplocbe.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Table(name = "Role")
@Entity
public class Role {

    @Id
    @Column(name = "role_id")
    private Integer roleId;

    @Column(name = "role_name", nullable = false)
    private String roleName;

    @Column(name = "role_description", length = 250)
    private String roleDescription;
}