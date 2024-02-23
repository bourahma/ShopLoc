package com.mimka.shoplocbe.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Time;
import java.time.LocalDateTime;

@Table(name = "QR_Code_Payment")
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class QRCodePayment {

    @Id
    @Column(name = "qr_code_payment_id")
    private String QRCodePaymentId;

    @OneToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "customer_id", referencedColumnName = "id")
    private Customer customer;

    @OneToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "order_id", referencedColumnName = "order_id")
    private Order order;

    @Column(name = "duration")
    private LocalDateTime duration;
}
