package com.mimka.shoplocbe.repositories;

import com.mimka.shoplocbe.entities.OrderProduct;
import com.mimka.shoplocbe.entities.OrderProductId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderProductRepository  extends JpaRepository<OrderProduct, OrderProductId> {


}
