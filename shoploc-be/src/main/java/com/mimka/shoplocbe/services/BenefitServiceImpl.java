package com.mimka.shoplocbe.services;

import com.mimka.shoplocbe.dto.benefit.BenefitDTO;
import com.mimka.shoplocbe.dto.benefit.BenefitDTOUtil;
import com.mimka.shoplocbe.entities.Benefit;
import com.mimka.shoplocbe.repositories.BenefitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BenefitServiceImpl implements BenefitService {

    private final BenefitRepository benefitRepository;

    private final BenefitDTOUtil benefitDTOUtil;

    @Autowired
    public BenefitServiceImpl(BenefitRepository benefitRepository, BenefitDTOUtil benefitDTOUtil) {
        this.benefitRepository = benefitRepository;
        this.benefitDTOUtil = benefitDTOUtil;
    }

    @Override
    public List<Benefit> getBenefits() {
        return this.benefitRepository.findByBenefitAvailable(true);
    }

    @Override
    public Benefit createBenefit(BenefitDTO benefitDTO) {
        Benefit benefit = this.benefitDTOUtil.toBenefit(benefitDTO);
        benefit = this.benefitRepository.save(benefit);

        return benefit;
    }
}
