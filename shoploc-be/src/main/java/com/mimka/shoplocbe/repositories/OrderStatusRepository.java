package com.mimka.shoplocbe.repositories;

import com.mimka.shoplocbe.entities.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface OrderStatusRepository extends JpaRepository<OrderStatus, Long> {

}
