package com.mimka.shoplocbe.facades;

import com.mimka.shoplocbe.dto.order.OrderDTO;
import com.mimka.shoplocbe.dto.order.OrderDTOUtil;
import com.mimka.shoplocbe.entities.Customer;
import com.mimka.shoplocbe.entities.Order;
import com.mimka.shoplocbe.entities.QRCodePayment;
import com.mimka.shoplocbe.exception.CommerceNotFoundException;
import com.mimka.shoplocbe.services.CustomerService;
import com.mimka.shoplocbe.services.OrderService;
import com.mimka.shoplocbe.services.QRCodePaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.security.Principal;
import java.util.Map;

@Component
public class OrderFacadeImpl implements OrderFacade {

    private final OrderService orderService;

    private final CustomerService customerService;

    private final QRCodePaymentService qrCodePaymentService;

    private final OrderDTOUtil orderDTOUtil;

    @Autowired
    public OrderFacadeImpl(OrderService orderService, CustomerService customerService, QRCodePaymentService qrCodePaymentService, OrderDTOUtil orderDTOUtil) {
        this.orderService = orderService;
        this.customerService = customerService;
        this.qrCodePaymentService = qrCodePaymentService;
        this.orderDTOUtil = orderDTOUtil;
    }

    @Override
    public OrderDTO createOrder(String customerUsername, OrderDTO orderDTO) throws CommerceNotFoundException {
        Customer customer = this.customerService.getCustomerByUsername(customerUsername);
        Order order = this.orderService.createOrder(customer, orderDTO);

        return this.orderDTOUtil.toOrderDTO(order);
    }

    @Override
    public OrderDTO settleOrder (String customerUsername, Long orderId, boolean usingPoints) {
        double total = this.orderService.getOrderTotalPrice(orderId, usingPoints);
        Order order = null;
        if (this.customerService.orderSettled(customerUsername, total, usingPoints)) {
            order = this.orderService.payOrder(orderId);
        }
        return this.orderDTOUtil.toOrderDTO(order);
    }

    @Override
    public Map<String,String> generateQrCode(long orderId, Principal principal) {
        Order order = this.orderService.getOrder(principal.getName(), orderId);
        Customer customer = this.customerService.getCustomerByUsername(principal.getName());
        QRCodePayment qrCodePayment = this.qrCodePaymentService.createQRCodePayment(order, customer);

        return Map.of("qr-code", qrCodePayment.getQRCodePaymentId());
    }

    @Override
    public Map<String,String> settleOrderUsingPointsQRCode(String qRCodeUUID) {
        QRCodePayment qrCodePayment = this.qrCodePaymentService.getQRCodePayment(qRCodeUUID);
        Order order = qrCodePayment.getOrder();
        Customer customer = qrCodePayment.getCustomer();
        this.settleOrder(customer.getUsername(), order.getOrderId(), true);
        this.qrCodePaymentService.deleteQRCodePayment(qrCodePayment.getQRCodePaymentId());

        return Map.of("paymentMessage", "Payment with points successfully completed.");
    }

    @Override
    public Map<String,String> settleOrderUsingBalanceQRCode(String qRCodeUUID) {
        QRCodePayment qrCodePayment = this.qrCodePaymentService.getQRCodePayment(qRCodeUUID);
        Order order = qrCodePayment.getOrder();
        Customer customer = qrCodePayment.getCustomer();
        this.settleOrder(customer.getUsername(), order.getOrderId(), false);
        this.qrCodePaymentService.deleteQRCodePayment(qrCodePayment.getQRCodePaymentId());

        return Map.of("paymentMessage", "Payment successfully completed.");
    }
}
