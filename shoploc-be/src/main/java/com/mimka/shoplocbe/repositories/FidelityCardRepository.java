package com.mimka.shoplocbe.repositories;

import com.mimka.shoplocbe.entities.FidelityCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FidelityCardRepository extends JpaRepository<FidelityCard, String> {

}
