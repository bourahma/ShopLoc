package com.mimka.shoplocbe.services;

import com.mimka.shoplocbe.dto.benefit.BenefitDTO;
import com.mimka.shoplocbe.entities.Benefit;

import java.util.List;

public interface BenefitService {

    List<Benefit> getBenefits ();

    Benefit createBenefit(BenefitDTO benefitDTO);
}
