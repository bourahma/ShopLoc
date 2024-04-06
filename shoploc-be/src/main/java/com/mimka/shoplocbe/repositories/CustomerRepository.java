package com.mimka.shoplocbe.repositories;

import com.mimka.shoplocbe.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Customer findByUsername(String username);

    Customer findByEmail(String email);
}
