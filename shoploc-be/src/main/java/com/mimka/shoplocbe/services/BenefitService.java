package com.mimka.shoplocbe.services;

import com.mimka.shoplocbe.dto.benefit.BenefitDTO;
import com.mimka.shoplocbe.entities.Benefit;
import com.mimka.shoplocbe.entities.BenefitHistory;
import com.mimka.shoplocbe.entities.Customer;

import java.util.List;

public interface BenefitService {

    List<Benefit> getBenefits ();

    Benefit getBenefit (Long benefitId);

    Benefit createBenefit(BenefitDTO benefitDTO);

    BenefitHistory availBenefit (Benefit benefit, Customer customer);

    List<BenefitHistory> customerBenefitHistory (Customer customer);
}
