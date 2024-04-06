package com.mimka.shoplocbe.services;

import com.mimka.shoplocbe.entities.Customer;
import com.mimka.shoplocbe.entities.Order;
import com.mimka.shoplocbe.entities.QRCodePayment;
import com.mimka.shoplocbe.repositories.QRCodePaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class QRCodePaymentServiceImpl implements  QRCodePaymentService {

    private final QRCodePaymentRepository qrCodePaymentRepository;

    @Autowired
    public QRCodePaymentServiceImpl(QRCodePaymentRepository qrCodePaymentRepository) {
        this.qrCodePaymentRepository = qrCodePaymentRepository;
    }

    @Override
    public QRCodePayment createQRCodePayment(Order order, Customer customer) {
        String uuid = UUID.randomUUID().toString();

        QRCodePayment qrCodePayment = new QRCodePayment();
        qrCodePayment.setCustomer(customer);
        qrCodePayment.setOrder(order);
        qrCodePayment.setQRCodePaymentId(uuid);
        LocalDateTime currentTimestamp = LocalDateTime.now();
        LocalDateTime futureTimestamp = currentTimestamp.plusMinutes(1);
        qrCodePayment.setDuration(futureTimestamp);

        this.qrCodePaymentRepository.save(qrCodePayment);

        return qrCodePayment;
    }

    @Override
    public void deleteQRCodePayment(String qRCodePaymentId) {
        this.qrCodePaymentRepository.deleteById(qRCodePaymentId);
    }

    @Override
    public QRCodePayment getQRCodePayment(String qRCodePaymentId) {
        return this.qrCodePaymentRepository.findById(qRCodePaymentId).get();
    }
}
