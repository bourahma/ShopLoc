package com.mimka.shoplocbe.batch.vfp;

import com.mimka.shoplocbe.entities.Customer;
import com.mimka.shoplocbe.entities.Order;
import com.mimka.shoplocbe.entities.VfpHistory;
import com.mimka.shoplocbe.services.VfpHistoryService;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class VFPStatusProcessor implements ItemProcessor<Pair<List<Order>, Customer>, Customer> {

    @Value("${vfp.minus.orders.to.enable}")
    private int minimumOrdersToEnableVFP;

    private final VfpHistoryService vfpHistoryService;

    @Autowired
    public VFPStatusProcessor(VfpHistoryService vfpHistoryService) {
        this.vfpHistoryService = vfpHistoryService;
    }

    @Override
    public Customer process(Pair<List<Order>, Customer> pair)  {
        return getCustomer(pair, this.minimumOrdersToEnableVFP);
    }

    public Customer getCustomer(Pair<List<Order>, Customer> pair, int minimumOrdersToEnableVFP) {
        Customer customer = pair.getSecond();

        if (pair.getFirst().size() >= minimumOrdersToEnableVFP) {
            customer.setVfpMembership(true);
            this.vfpHistoryService.grantVFPStatus(customer);
        } else {
            VfpHistory vfpHistory = this.vfpHistoryService.lastVFPStatusGranted(customer);
            if (vfpHistory != null && vfpHistory.getExpirationDate().isBefore(LocalDate.now())) {
                customer.setVfpMembership(true);
            } else if (vfpHistory == null || vfpHistory.getExpirationDate().isAfter(LocalDate.now())){
                customer.setVfpMembership(true);
            }
        }
        customer.setVfpUsed(false);

        return customer;
    }
}
