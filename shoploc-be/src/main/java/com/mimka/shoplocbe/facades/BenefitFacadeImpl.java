package com.mimka.shoplocbe.facades;

import com.mimka.shoplocbe.dto.benefit.BenefitDTO;
import com.mimka.shoplocbe.dto.benefit.BenefitDTOUtil;
import com.mimka.shoplocbe.entities.Benefit;
import com.mimka.shoplocbe.services.BenefitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BenefitFacadeImpl implements BenefitFacade {

    private final BenefitService benefitService;

    private final BenefitDTOUtil benefitDTOUtil;

    @Autowired
    public BenefitFacadeImpl(BenefitService benefitService, BenefitDTOUtil benefitDTOUtil) {
        this.benefitService = benefitService;
        this.benefitDTOUtil = benefitDTOUtil;
    }

    @Override
    public List<BenefitDTO> getBenefits() {
        List<Benefit> benefits = this.benefitService.getBenefits();

        return benefits
                .stream()
                .map(benefitDTOUtil::toBenefitDTO).toList();
    }

    @Override
    public BenefitDTO createBenefit(BenefitDTO benefitDTO) {
        Benefit benefit = this.benefitService.createBenefit(benefitDTO);

        return this.benefitDTOUtil.toBenefitDTO(benefit);
    }
}
