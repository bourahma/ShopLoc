package com.mimka.shoplocbe.repositories;

import com.mimka.shoplocbe.entities.ProductCart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ProductCartRepository extends JpaRepository<ProductCart, Long> {

}