package com.mimka.shoplocbe.repositories;

import com.mimka.shoplocbe.entities.BalanceTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BalanceTransactionRepository extends JpaRepository<BalanceTransaction, Long> {

}
