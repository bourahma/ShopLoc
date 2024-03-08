package com.mimka.shoplocbe.repositories;

import com.mimka.shoplocbe.entities.BenefitHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BenefitHistoryRepository extends JpaRepository<BenefitHistory, Long> {

}
