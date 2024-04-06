package com.mimka.shoplocbe.repositories;

import com.mimka.shoplocbe.entities.BenefitHistory;
import com.mimka.shoplocbe.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BenefitHistoryRepository extends JpaRepository<BenefitHistory, Long> {

    List<BenefitHistory> findByCustomer (Customer customer);

}
