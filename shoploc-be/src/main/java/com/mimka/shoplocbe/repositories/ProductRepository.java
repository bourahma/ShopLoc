package com.mimka.shoplocbe.repositories;

import com.mimka.shoplocbe.entities.Commerce;
import com.mimka.shoplocbe.entities.CommerceType;
import com.mimka.shoplocbe.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByCommerceAndGiftIsTrue(Commerce commerce);

    List<Product> findByCommerce_CommerceType(CommerceType commerceType);

    List<Product> findByGiftIsTrue( );

}
