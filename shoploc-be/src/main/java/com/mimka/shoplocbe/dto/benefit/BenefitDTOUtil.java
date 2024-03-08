package com.mimka.shoplocbe.dto.benefit;

import com.mimka.shoplocbe.entities.Benefit;
import com.mimka.shoplocbe.entities.BenefitHistory;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BenefitDTOUtil {

    private ModelMapper modelMapper;

    @Autowired
    BenefitDTOUtil (ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public BenefitDTO toBenefitDTO (Benefit benefit) {
        return modelMapper.map(benefit, BenefitDTO.class);
    }

    public Benefit toBenefit (BenefitDTO benefitDTO) {
        return modelMapper.map(benefitDTO, Benefit.class);
    }

    public BenefitHistoryDTO toBenefitHistoryDTO (BenefitHistory benefitHistory) {
        return modelMapper.map(benefitHistory, BenefitHistoryDTO.class);
    }


}
