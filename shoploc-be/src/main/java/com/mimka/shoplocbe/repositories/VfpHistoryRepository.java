package com.mimka.shoplocbe.repositories;

import com.mimka.shoplocbe.entities.Customer;
import com.mimka.shoplocbe.entities.VfpHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VfpHistoryRepository extends JpaRepository<VfpHistory, Long> {

    VfpHistory findTopByCustomerOrderByGrantedDateDesc(Customer customer);

    List<VfpHistory> findByCustomer (Customer customer);

}

