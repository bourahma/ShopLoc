package com.mimka.shoplocbe.services;

import com.mimka.shoplocbe.dto.benefit.BenefitDTO;
import com.mimka.shoplocbe.dto.benefit.BenefitDTOUtil;
import com.mimka.shoplocbe.entities.Benefit;
import com.mimka.shoplocbe.entities.BenefitHistory;
import com.mimka.shoplocbe.entities.Customer;
import com.mimka.shoplocbe.repositories.BenefitHistoryRepository;
import com.mimka.shoplocbe.repositories.BenefitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

@Service
public class BenefitServiceImpl implements BenefitService {

    private final BenefitRepository benefitRepository;

    private final BenefitHistoryRepository benefitHistoryRepository;

    private final BenefitDTOUtil benefitDTOUtil;

    @Autowired
    public BenefitServiceImpl(BenefitRepository benefitRepository, BenefitHistoryRepository benefitHistoryRepository, BenefitDTOUtil benefitDTOUtil) {
        this.benefitRepository = benefitRepository;
        this.benefitHistoryRepository = benefitHistoryRepository;
        this.benefitDTOUtil = benefitDTOUtil;
    }

    @Override
    public List<Benefit> getBenefits() {
        return this.benefitRepository.findByBenefitAvailable(true);
    }

    @Override
    public Benefit getBenefit(Long benefitId) {
        return this.benefitRepository.findById(benefitId).get();
    }

    @Override
    public Benefit createBenefit(BenefitDTO benefitDTO) {
        Benefit benefit = this.benefitDTOUtil.toBenefit(benefitDTO);
        benefit = this.benefitRepository.save(benefit);

        return benefit;
    }

    @Override
    public BenefitHistory availBenefit(Benefit benefit, Customer customer) {
        BenefitHistory benefitHistory = new BenefitHistory();
        benefitHistory.setCustomer(customer);
        benefitHistory.setBenefit(benefit);
        benefitHistory.setAcquisitionDate(LocalDate.now());
        benefitHistory.setAcquisitionTime(LocalTime.now());
        benefitHistory.setQrCode(UUID.randomUUID().toString());

        benefitHistory = this.benefitHistoryRepository.save(benefitHistory);

        return benefitHistory;
    }

    @Override
    public List<BenefitHistory> customerBenefitHistory(Customer customer) {
        return this.benefitHistoryRepository.findByCustomer(customer);
    }
}
