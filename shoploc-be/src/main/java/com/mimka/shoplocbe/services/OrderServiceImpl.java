package com.mimka.shoplocbe.services;

import com.mimka.shoplocbe.dto.order.OrderDTO;
import com.mimka.shoplocbe.dto.order.OrderDTOUtil;
import com.mimka.shoplocbe.dto.order.OrderProductDTO;
import com.mimka.shoplocbe.entities.*;
import com.mimka.shoplocbe.exception.CommerceNotFoundException;
import com.mimka.shoplocbe.repositories.OrderRepository;
import com.mimka.shoplocbe.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final CommerceService commerceService;
    private final OrderDTOUtil orderDTOUtil;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository, CommerceService commerceService, OrderDTOUtil orderDTOUtil, ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.commerceService = commerceService;
        this.orderDTOUtil = orderDTOUtil;
        this.productRepository = productRepository;
    }

    @Override
    public Order createOrder(Customer customer, OrderDTO orderDTO) throws CommerceNotFoundException {
        Order order = new Order();
        order.setCustomer(customer);
        order.setCommerce(this.commerceService.getCommerce(orderDTO.getCommerceId()));
        order.setOrderDate(LocalDate.now());
        order.setOrderStatus(OrderStatus.PENDING.name());

        Set<OrderProduct> orderProducts = new HashSet<>();
        for (OrderProductDTO orderProductDTO : orderDTO.getProducts()) {
            OrderProduct orderProduct = this.orderDTOUtil.toOrderProduct(order.getCommerce().getCommerceId(), orderProductDTO);
            orderProduct.setOrder(order);
            orderProduct.setProduct(this.productRepository.findById(orderProductDTO.getProductId()).get());
            orderProducts.add(orderProduct);
        }
        order.setOrderProducts(orderProducts);
        this.orderRepository.save(order);

        return order;
    }

    @Override
    public Order getOrder(String principal, long orderId) {
        return this.orderRepository.findById(orderId).orElse(null);
    }

    @Override
    public Order payOrder(Long orderId) {
        Order order = this.orderRepository.findById(orderId).get();

        if (order.getOrderStatus().equals(OrderStatus.PENDING.name())) {
            order.setOrderStatus(OrderStatus.PAID.name());
            this.orderRepository.save(order);
        }
        return order;
    }

    @Override
    public double getOrderTotalPrice(Long orderId, boolean usingPoints) {
        return this.getOrderTotal(orderId, usingPoints);
    }

    @Override
    public double getOrderTotalPointsPrice(Long orderId) {
        return this.getOrderTotal(orderId, true);
    }

    public double getOrderTotal(Long orderId, boolean usePointsPrice) {
        Order order = this.orderRepository.findById(orderId).orElse(null);

        if (order == null || !order.getOrderStatus().equals(OrderStatus.PENDING.name())) {
            return 0.0;
        }

        double totalOrderPrice = 0.0;

        for (OrderProduct orderProduct : order.getOrderProducts()) {
            double productPrice = usePointsPrice ? orderProduct.getProduct().getRewardPointsPrice()
                    : orderProduct.getProduct().getPrice();
            totalOrderPrice += orderProduct.getQuantity() * productPrice;
        }

        return totalOrderPrice;
    }
}
