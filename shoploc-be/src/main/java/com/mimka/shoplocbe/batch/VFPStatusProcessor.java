package com.mimka.shoplocbe.batch;

import com.mimka.shoplocbe.entities.Customer;
import com.mimka.shoplocbe.entities.Order;
import com.mimka.shoplocbe.repositories.CustomerRepository;
import jakarta.transaction.Transactional;
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
        Customer customer = pair.getSecond();

        if (pair.getFirst().size() >= this.minimumOrdersToEnableVFP) {
            customer.setVfpMembership(true);
        } else if (pair.getFirst() == null  || pair.getFirst().size() <= this.minimumOrdersToEnableVFP) {
            customer.setVfpMembership(false);
        }

        return customer;
    }
}
