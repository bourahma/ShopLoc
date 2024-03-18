package com.mimka.shoplocbe.repositories;

import com.mimka.shoplocbe.entities.Benefit;
import jdk.jfr.Registered;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

@Registered
public interface BenefitRepository extends JpaRepository<Benefit, Long> {

    List<Benefit> findByBenefitAvailable (Boolean availability);

}
