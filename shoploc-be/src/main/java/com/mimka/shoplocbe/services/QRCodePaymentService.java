package com.mimka.shoplocbe.services;

import com.mimka.shoplocbe.entities.Customer;
import com.mimka.shoplocbe.entities.Order;
import com.mimka.shoplocbe.entities.QRCodePayment;

public interface QRCodePaymentService {

    QRCodePayment createQRCodePayment(Order order, Customer customer);

    void deleteQRCodePayment (String qRCodePaymentId);

    QRCodePayment getQRCodePayment (String qRCodePaymentId);
}
