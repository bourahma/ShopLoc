package com.mimka.shoplocbe.facades;

import com.mimka.shoplocbe.dto.benefit.BenefitDTO;

import java.util.List;

public interface BenefitFacade {

    List<BenefitDTO> getBenefits ();

    BenefitDTO createBenefit (BenefitDTO benefitDTO);
}
