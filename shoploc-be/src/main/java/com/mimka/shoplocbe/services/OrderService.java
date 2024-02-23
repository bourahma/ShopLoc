package com.mimka.shoplocbe.services;

import com.mimka.shoplocbe.dto.order.OrderDTO;
import com.mimka.shoplocbe.entities.Customer;
import com.mimka.shoplocbe.entities.Order;
import com.mimka.shoplocbe.exception.CommerceException;

public interface OrderService {

    Order createOrder(Customer customer, OrderDTO orderDTO) throws CommerceException;

    Order payOrder (Long orderId);

    double getOrderTotalPrice (Long orderId);

    double getOrderTotalPointsPrice(Long orderId);

    Order getOrder(String principal, long orderId);
}
