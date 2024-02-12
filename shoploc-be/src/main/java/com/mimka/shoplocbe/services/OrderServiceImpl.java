package com.mimka.shoplocbe.services;

import com.mimka.shoplocbe.dto.order.OrderDTO;
import com.mimka.shoplocbe.dto.order.OrderDTOUtil;
import com.mimka.shoplocbe.dto.order.OrderProductDTO;
import com.mimka.shoplocbe.entities.Customer;
import com.mimka.shoplocbe.entities.Order;
import com.mimka.shoplocbe.entities.OrderProduct;
import com.mimka.shoplocbe.repositories.OrderRepository;
import com.mimka.shoplocbe.repositories.OrderStatusRepository;
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
    private final OrderStatusRepository orderStatusRepository;
    private final CustomerService customerService;
    private final CommerceService commerceService;
    private final OrderDTOUtil orderDTOUtil;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository, CustomerService customerService, CommerceService commerceService, OrderDTOUtil orderDTOUtil, ProductRepository productRepository, OrderStatusRepository orderStatusRepository) {
        this.orderRepository = orderRepository;
        this.customerService = customerService;
        this.commerceService = commerceService;
        this.orderDTOUtil = orderDTOUtil;
        this.productRepository = productRepository;
        this.orderStatusRepository = orderStatusRepository;
    }

    @Override
    public OrderDTO createOrder(String principal, OrderDTO orderDTO) {
        Customer customer = this.customerService.getCustomerByUsername(principal);

        Order order = new Order();
        order.setCustomer(customer);
        order.setCommerce(this.commerceService.getCommerce(orderDTO.getCommerceId()));
        order.setOrderDate(LocalDate.now());
        order.setOrderStatus(this.orderStatusRepository.getReferenceById(1L));

        Set<OrderProduct> orderProducts = new HashSet<>();
        for (OrderProductDTO orderProductDTO : orderDTO.getProducts()) {
            OrderProduct orderProduct = this.orderDTOUtil.toOrderProduct(order.getCommerce().getCommerceId(), orderProductDTO);
            orderProduct.setOrder(order);
            orderProduct.setProduct(this.productRepository.findById(orderProductDTO.getProductId()).get());
            orderProducts.add(orderProduct);
        }
        order.setOrderProducts(orderProducts);
        this.orderRepository.save(order);

        return this.orderDTOUtil.toOrderDTO(order);
    }

    @Override
    public OrderDTO getOrder(String principal, long orderId) {
        return this.orderDTOUtil.toOrderDTO(this.orderRepository.findById(orderId).get());
    }
}
