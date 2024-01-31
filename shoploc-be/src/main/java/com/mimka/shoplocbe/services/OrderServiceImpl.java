package com.mimka.shoplocbe.services;

import com.mimka.shoplocbe.dto.order.OrderDTO;
import com.mimka.shoplocbe.entities.Order;
import com.mimka.shoplocbe.entities.User;
import com.mimka.shoplocbe.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    private final UserService userService;

    private final CommerceService commerceService;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository, UserService userService, CommerceService commerceService) {
        this.orderRepository = orderRepository;
        this.userService = userService;
        this.commerceService = commerceService;
    }

    @Override
    public void createOrder(String principal, OrderDTO orderDTO) {
        User user = this.userService.getUserByUsername(principal);

        Order order = new Order();
        order.setUser(user);
        order.setCommerce(this.commerceService.getCommerce(orderDTO.getCommerceId()));
        order.setOrderDate(LocalDate.now());
        order.setStatus("passed");

        this.orderRepository.save(order);
    }
}
