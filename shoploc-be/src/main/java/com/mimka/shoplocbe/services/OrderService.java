package com.mimka.shoplocbe.services;

import com.mimka.shoplocbe.dto.order.OrderDTO;
import com.mimka.shoplocbe.entities.Order;

public interface OrderService {

    OrderDTO createOrder(String principal, OrderDTO orderDTO);

    OrderDTO getOrder(String principal, long orderId);
}
