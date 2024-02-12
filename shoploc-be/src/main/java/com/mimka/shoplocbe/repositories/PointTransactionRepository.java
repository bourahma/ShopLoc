package com.mimka.shoplocbe.repositories;

import com.mimka.shoplocbe.entities.PointTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PointTransactionRepository extends JpaRepository<PointTransaction, Long > {
    //List<PointTransaction> findByCustomerAndType(User customer, TransactionType transactionType);
}
