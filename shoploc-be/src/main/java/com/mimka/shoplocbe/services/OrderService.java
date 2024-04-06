package com.mimka.shoplocbe.services;

import com.mimka.shoplocbe.dto.order.OrderDTO;
import com.mimka.shoplocbe.entities.Customer;
import com.mimka.shoplocbe.entities.Order;
import com.mimka.shoplocbe.exception.CommerceNotFoundException;
import com.mimka.shoplocbe.exception.ProductException;

public interface OrderService {

    Order createOrder(Customer customer, OrderDTO orderDTO) throws CommerceNotFoundException;

    Order payOrder (Long orderId) throws ProductException;

    double getOrderTotalPrice (Long orderId, boolean usingPoints);

    Order getOrder(String principal, long orderId);

    Order getOrder(long orderId);
}
