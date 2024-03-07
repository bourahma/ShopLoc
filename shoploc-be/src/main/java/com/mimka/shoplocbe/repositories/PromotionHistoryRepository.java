package com.mimka.shoplocbe.repositories;

import com.mimka.shoplocbe.entities.PromotionHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PromotionHistoryRepository extends JpaRepository<PromotionHistory, Long> {

}
