package com.mimka.shoplocbe.services;

import com.mimka.shoplocbe.entities.Customer;
import com.mimka.shoplocbe.entities.VfpHistory;

import java.util.List;

public interface VfpHistoryService {

    void grantVFPStatus (Customer customer);

    VfpHistory lastVFPStatusGranted (Customer customer);

    List<VfpHistory> customerVFPMembershipHistory (Customer customer);
}
