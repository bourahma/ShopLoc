package com.mimka.shoplocbe.bi;

import com.mimka.shoplocbe.entities.*;
import com.mimka.shoplocbe.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Component
public class BiGiftComponent {
    private final GiftHistoryRepository giftHistoryRepository;
    private final ProductRepository productRepository;
    private final CustomerRepository customerRepository;
    private final OrderRepository orderRepository;
    private final VfpHistoryRepository vfpHistoryRepository;

    private final PointTransactionRepository pointTransactionRepository;


    @Autowired
    public BiGiftComponent(GiftHistoryRepository giftHistoryRepository,
                           ProductRepository productRepository,
                           CustomerRepository customerRepository,
                           OrderRepository orderRepository,
                           VfpHistoryRepository vfpHistoryRepository, PointTransactionRepository pointTransactionRepository) {
        this.giftHistoryRepository = giftHistoryRepository;
        this.productRepository = productRepository;
        this.customerRepository = customerRepository;
        this.orderRepository = orderRepository;
        this.vfpHistoryRepository = vfpHistoryRepository;
        this.pointTransactionRepository = pointTransactionRepository;
    }

    public void process () {
        List<Customer> customers = customerRepository.findAll();

        for (Customer customer : customers) {
            if (!customer.getEnabled()) {
                continue;
            }

            LocalDate processDate = customer.getSubscriptionDate();
            LocalDate today = LocalDate.now();

            while (processDate.isBefore(today)) {
                acquireGift(customer, processDate);
                processDate = processDate.plusDays(5);
            }
        }
    }

    private void acquireGift (Customer customer, LocalDate acquireDate) {
        List<Product> gifts = productRepository.findByGiftIsTrue();
        List<VfpHistory> vfpHistories = vfpHistoryRepository.findByCustomer(customer);
        List<Order> customerOrders = orderRepository.findByCustomerAndAndOrderDateAfterAndAndOrderStatus(customer, acquireDate, OrderStatus.PAID.name());

        double earned = pointTransactionRepository.findPointTransactionsByTypeAndTransactionDateIsBefore(TransactionType.EARNED, acquireDate.atTime(23,59)).stream()
                .mapToDouble(PointTransaction::getAmount)
                .sum();

        double spent = pointTransactionRepository.findPointTransactionsByTypeAndTransactionDateIsBefore(TransactionType.SPENT, acquireDate.atTime(23,59)).stream()
                .mapToDouble(PointTransaction::getAmount)
                .sum();
        for (VfpHistory vfpHistory : vfpHistories) {
            boolean added = false;
            if (acquireDate.isAfter(vfpHistory.getGrantedDate()) && acquireDate.isBefore(vfpHistory.getExpirationDate())) {
                for (Product gift : gifts) {
                    for (Order order : customerOrders) {
                        if (Objects.equals(gift.getCommerce().getCommerceId(), order.getCommerce().getCommerceId()) && !added) {
                            GiftHistory giftHistory = new GiftHistory();
                            giftHistory.setCustomer(customer);
                            giftHistory.setProduct(gift);
                            giftHistory.setPurchaseDate(acquireDate);
                            if ((earned - spent) >= gift.getRewardPointsPrice()) {
                                added = true;
                                PointTransaction pointTransaction = new PointTransaction();
                                pointTransaction.setAmount(gift.getRewardPointsPrice());
                                pointTransaction.setCommerce(gift.getCommerce());
                                pointTransaction.setType(TransactionType.SPENT);
                                pointTransaction.setTransactionDate(acquireDate.atTime(23,59));
                                pointTransaction.setFidelityCard(customer.getFidelityCard());

                                pointTransactionRepository.save(pointTransaction);
                                giftHistoryRepository.save(giftHistory);
                                break;
                            }
                        }
                    }
                }
            }

        }
    }
}