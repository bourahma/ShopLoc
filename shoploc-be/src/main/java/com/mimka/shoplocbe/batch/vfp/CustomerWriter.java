package com.mimka.shoplocbe.batch.vfp;

import com.mimka.shoplocbe.entities.Customer;
import com.mimka.shoplocbe.repositories.CustomerRepository;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

@Component
public class CustomerWriter implements ItemWriter<Customer> {

    private final CustomerRepository customerRepository;

    public CustomerWriter(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public void write(Chunk<? extends Customer> chunk) {
        this.customerRepository.saveAll(chunk.getItems());
    }
}