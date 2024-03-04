package com.mimka.shoplocbe.batch;

import com.mimka.shoplocbe.entities.Customer;
import com.mimka.shoplocbe.entities.Order;
import com.mimka.shoplocbe.entities.OrderStatus;
import com.mimka.shoplocbe.repositories.CustomerRepository;
import com.mimka.shoplocbe.repositories.OrderRepository;
import jakarta.transaction.Transactional;
import org.springframework.batch.item.ItemReader;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Iterator;
import java.util.List;

@Component
public class CustomerOrdersReader implements ItemReader<Pair<List<Order>, Customer>> {
    private OrderRepository orderRepository;
    private CustomerRepository customerRepository;
    private List<Customer> customers;
    private Iterator<Customer> customerIterator;

    public CustomerOrdersReader(OrderRepository orderRepository, CustomerRepository customerRepository) {
        this.orderRepository = orderRepository;
        this.customerRepository = customerRepository;
    }

    public void init () {
        this.customers = this.customerRepository.findAll();
        this.customerIterator = customers.iterator();
    }

    @Override
    @Transactional
    public Pair<List<Order>, Customer> read() {
        if (customers == null || !customerIterator.hasNext()) {
            return null;
        }

        Customer currentCustomer = customerIterator.next();
        return Pair.of(orderRepository.findByCustomerAndAndOrderDateAfterAndAndOrderStatus(currentCustomer, LocalDate.now().minusDays(7), OrderStatus.PAID.name()), currentCustomer);
    }
}