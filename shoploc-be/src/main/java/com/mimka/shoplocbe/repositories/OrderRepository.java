package com.mimka.shoplocbe.repositories;

import com.mimka.shoplocbe.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    @Query(value = "SELECT nextval('order_seq')", nativeQuery = true)
    Long getNextSequenceValue();
}
