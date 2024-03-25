package com.mimka.shoplocbe.repositories;

import com.mimka.shoplocbe.entities.PointTransaction;
import com.mimka.shoplocbe.entities.TransactionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PointTransactionRepository extends JpaRepository<PointTransaction, Long > {

    List<PointTransaction> findPointTransactionsByTypeAndTransactionDateIsBefore (TransactionType type, LocalDateTime localDate);

}
