package com.mimka.shoplocbe.services;

import com.mimka.shoplocbe.entities.Customer;
import com.mimka.shoplocbe.entities.VfpHistory;
import com.mimka.shoplocbe.repositories.VfpHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class VfpHistoryServiceImpl implements VfpHistoryService {

    private final VfpHistoryRepository vfpHistoryRepository;

    @Autowired
    public VfpHistoryServiceImpl(VfpHistoryRepository vfpHistoryRepository) {
        this.vfpHistoryRepository = vfpHistoryRepository;
    }

    @Override
    public void grantVFPStatus(Customer customer) {
        VfpHistory vfpHistory = new VfpHistory ();
        vfpHistory.setGrantedDate(LocalDate.now());
        vfpHistory.setExpirationDate(LocalDate.now().plusDays(7));
        vfpHistory.setCustomer(customer);

        this.vfpHistoryRepository.save(vfpHistory);
    }

    @Override
    public VfpHistory lastVFPStatusGranted(Customer customer) {
        return this.vfpHistoryRepository.findTopByCustomerOrderByGrantedDateDesc(customer);
    }

    @Override
    public List<VfpHistory> customerVFPMembershipHistory(Customer customer) {
        return this.vfpHistoryRepository.findByCustomer(customer);
    }
}
