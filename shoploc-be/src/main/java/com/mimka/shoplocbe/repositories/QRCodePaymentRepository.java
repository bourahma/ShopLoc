package com.mimka.shoplocbe.repositories;

import com.mimka.shoplocbe.entities.QRCodePayment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QRCodePaymentRepository extends JpaRepository<QRCodePayment, String> {

}
