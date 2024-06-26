package com.mimka.shoplocbe.repositories;

import com.mimka.shoplocbe.entities.Commerce;
import com.mimka.shoplocbe.entities.Promotion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface PromotionRepository extends JpaRepository<Promotion, Long> {

    List<Promotion> findAllByCommerce(Commerce commerce);

    List<Promotion> findPromotionByEndDateAfterAndSentIsFalse(LocalDate localDate);
}
