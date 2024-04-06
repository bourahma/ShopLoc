package com.mimka.shoplocbe.repositories;

import com.mimka.shoplocbe.entities.Customer;
import com.mimka.shoplocbe.entities.GiftHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GiftHistoryRepository extends JpaRepository<GiftHistory, Long> {

    List<GiftHistory> findByCustomer (Customer customer);

}
