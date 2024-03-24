package com.mimka.shoplocbe.bi;

import com.mimka.shoplocbe.entities.Customer;
import com.mimka.shoplocbe.entities.CustomerConnection;
import com.mimka.shoplocbe.repositories.CustomerConnectionRepository;
import com.mimka.shoplocbe.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Random;

@Component
public class BiCustomerConnectionComponent {
    private final CustomerRepository customerRepository;
    private final CustomerConnectionRepository customerConnectionRepository;
    private final Random random = new Random();

    @Autowired
    public BiCustomerConnectionComponent (CustomerRepository customerRepository, CustomerConnectionRepository customerConnectionRepository) {
        this.customerRepository = customerRepository;
        this.customerConnectionRepository = customerConnectionRepository;
    }

    public void process () {
        for (Customer customer : customerRepository.findAll()) {
            generateConnectionsForCustomer(customer);
        }
    }

    private void generateConnectionsForCustomer(Customer customer) {
        LocalDate subscriptionDate = customer.getSubscriptionDate();
        LocalDate today = LocalDate.now();

        while (isValidForConnection(subscriptionDate, today, customer.getEnabled())) {
            if (random.nextInt(2) == 1) {
                createAndSaveConnection(customer, subscriptionDate);
            }
            subscriptionDate = subscriptionDate.plusDays(1);
        }
    }

    private boolean isValidForConnection(LocalDate subscriptionDate, LocalDate today, boolean isEnabled) {
        return subscriptionDate != null && !subscriptionDate.isAfter(today) && isEnabled;
    }

    private void createAndSaveConnection(Customer customer, LocalDate date) {
        LocalDateTime connectTime = generateRandomDateTime(date);
        // Generate a disconnect time up to 45 minutes later
        LocalDateTime disconnectTime = connectTime.plusMinutes(random.nextInt(46));

        CustomerConnection connection = new CustomerConnection();
        connection.setCustomer(customer);
        connection.setConnectTime(connectTime);
        connection.setDisconnectTime(disconnectTime);

        customerConnectionRepository.save(connection);
    }

    private LocalDateTime generateRandomDateTime(LocalDate date) {
        int hour = random.nextInt(24); // Hours are 0-23
        int minute = random.nextInt(60); // Minutes are 0-59
        int second = random.nextInt(60); // Seconds are 0-59
        return LocalDateTime.of(date, LocalTime.of(hour, minute, second));
    }
}
