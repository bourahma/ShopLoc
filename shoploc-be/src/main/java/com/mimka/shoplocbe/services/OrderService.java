package com.mimka.shoplocbe.services;

import com.mimka.shoplocbe.dto.order.OrderDTO;

public interface OrderService {

    void createOrder (String principal, OrderDTO orderDTO);
}
