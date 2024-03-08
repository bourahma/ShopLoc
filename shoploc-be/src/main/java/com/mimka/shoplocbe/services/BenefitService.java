package com.mimka.shoplocbe.services;

import com.mimka.shoplocbe.dto.benefit.BenefitDTO;
import com.mimka.shoplocbe.entities.Benefit;
import com.mimka.shoplocbe.entities.BenefitHistory;
import com.mimka.shoplocbe.entities.Customer;
import com.mimka.shoplocbe.exception.BenefitException;

import java.util.List;

public interface BenefitService {

    List<Benefit> getBenefits ();

    Benefit getBenefit (Long benefitId) throws BenefitException;

    Benefit createBenefit(BenefitDTO benefitDTO);

    BenefitHistory availBenefit (Benefit benefit, Customer customer);

    List<BenefitHistory> customerBenefitHistory (Customer customer);
}
