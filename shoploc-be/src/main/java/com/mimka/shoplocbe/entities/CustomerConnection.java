package com.mimka.shoplocbe.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Time;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Customer_Connection")
public class CustomerConnection {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "customer_connection_sequence")
    @SequenceGenerator(name = "customer_connection_sequence", sequenceName = "customer_connection_sequence", allocationSize = 1, initialValue = 50)
    @Column(name = "connection_id")
    private Long connectionId;

    @Column(name = "connect_time")
    private LocalDateTime connectTime;

    @Column(name = "disconnect_time")
    private LocalDateTime disconnectTime;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;
}
