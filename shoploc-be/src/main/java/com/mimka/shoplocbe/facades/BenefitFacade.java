package com.mimka.shoplocbe.facades;

import com.mimka.shoplocbe.dto.benefit.BenefitDTO;
import com.mimka.shoplocbe.dto.benefit.BenefitHistoryDTO;
import com.mimka.shoplocbe.exception.BenefitException;

import java.util.List;

public interface BenefitFacade {

    List<BenefitDTO> getBenefits ();

    BenefitHistoryDTO availBenefit (String customerUsername, long benefitId) throws BenefitException;

    BenefitDTO createBenefit (BenefitDTO benefitDTO);

    List<BenefitHistoryDTO> customerBenefitHistory (String customerUsername);
}
