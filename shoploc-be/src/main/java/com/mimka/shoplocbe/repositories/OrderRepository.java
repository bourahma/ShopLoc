package com.mimka.shoplocbe.repositories;

import com.mimka.shoplocbe.entities.Customer;
import com.mimka.shoplocbe.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findByCustomerAndAndOrderDateAfterAndAndOrderStatus(Customer customer, LocalDate orderDate, String orderStatus);

    List<Order> findByCustomerAndOrderDateBeforeAndOrderStatus (Customer customer, LocalDate orderDate, String orderStatus);

    Order findByOrderIdAndOrderStatus(Long orderId, String name);
}
