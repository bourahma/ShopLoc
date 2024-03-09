package com.mimka.shoplocbe.facades;

import com.mimka.shoplocbe.dto.benefit.BenefitDTO;
import com.mimka.shoplocbe.dto.benefit.BenefitDTOUtil;
import com.mimka.shoplocbe.dto.benefit.BenefitHistoryDTO;
import com.mimka.shoplocbe.entities.Benefit;
import com.mimka.shoplocbe.entities.BenefitHistory;
import com.mimka.shoplocbe.entities.Customer;
import com.mimka.shoplocbe.exception.BenefitException;
import com.mimka.shoplocbe.services.BenefitService;
import com.mimka.shoplocbe.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BenefitFacadeImpl implements BenefitFacade {

    private final BenefitService benefitService;

    private final CustomerService customerService;

    private final BenefitDTOUtil benefitDTOUtil;

    @Autowired
    public BenefitFacadeImpl(BenefitService benefitService, CustomerService customerService, BenefitDTOUtil benefitDTOUtil) {
        this.benefitService = benefitService;
        this.customerService = customerService;
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
    public BenefitHistoryDTO availBenefit(String customerUsername, long benefitId) throws BenefitException {
        Customer customer = this.customerService.getCustomerByUsername(customerUsername);
        Benefit benefit = this.benefitService.getBenefit(benefitId);

        if (!customer.isVfpMembership()) {
            throw new BenefitException("Les avantages sont réservés aux membres VFP.");
        }

        BenefitHistory benefitHistory = this.benefitService.availBenefit(benefit, customer);


        return this.benefitDTOUtil.toBenefitHistoryDTO(benefitHistory);
    }

    @Override
    public BenefitDTO createBenefit(BenefitDTO benefitDTO) {
        Benefit benefit = this.benefitService.createBenefit(benefitDTO);
        return this.benefitDTOUtil.toBenefitDTO(benefit);
    }

    @Override
    public List<BenefitHistoryDTO> customerBenefitHistory(String customerUsername) {
        Customer customer = this.customerService.getCustomerByUsername(customerUsername);

        List<BenefitHistory> customerBenefitHistory = this.benefitService.customerBenefitHistory(customer);

        return customerBenefitHistory
                .stream()
                .map(benefitDTOUtil::toBenefitHistoryDTO).toList();
    }
}
