package com.mimka.shoplocbe.batch.vfp;

import com.mimka.shoplocbe.entities.Customer;
import com.mimka.shoplocbe.entities.Order;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class VFPStatusProcessor implements ItemProcessor<Pair<List<Order>, Customer>, Customer> {

    @Value("${vfp.minus.orders.to.enable}")
    private int minimumOrdersToEnableVFP;
    @Override
    public Customer process(Pair<List<Order>, Customer> pair)  {
        return getCustomer(pair, this.minimumOrdersToEnableVFP);
    }

    public Customer getCustomer(Pair<List<Order>, Customer> pair, int minimumOrdersToEnableVFP) {
        Customer customer = pair.getSecond();

        if (pair.getFirst().size() >= minimumOrdersToEnableVFP) {
            customer.setVfpMembership(true);
        } else if (pair.getFirst() == null  || pair.getFirst().size() <= minimumOrdersToEnableVFP) {
            customer.setVfpMembership(false);
        }
        customer.setVfpUsed(false);

        return customer;
    }
}
