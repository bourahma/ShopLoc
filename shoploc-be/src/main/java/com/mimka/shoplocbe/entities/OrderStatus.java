package com.mimka.shoplocbe.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "Order_Status")
public class OrderStatus {

    @Id
    @Column(name = "commande_status_id")
    private Long orderStatusId;

    @Column(name = "label")
    private String label;

    @Column(name = "description")
    private String description;
}
