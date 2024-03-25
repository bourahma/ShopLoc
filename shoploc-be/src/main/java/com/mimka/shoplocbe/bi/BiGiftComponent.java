package com.mimka.shoplocbe.bi;

import com.mimka.shoplocbe.entities.*;
import com.mimka.shoplocbe.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

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
        System.out.println("------------------ > 1");
        for (VfpHistory vfpHistory : vfpHistories) {
            System.out.println("------------------ > 2");
            if (acquireDate.isAfter(vfpHistory.getGrantedDate()) && acquireDate.isBefore(vfpHistory.getExpirationDate())) {
                System.out.println("------------------ > 3");
                for (Product gift : gifts) {
                    System.out.println("------------------ > 4");
                    for (Order order : customerOrders) {
                        System.out.println("------------------ > 5");
                        if (Objects.equals(gift.getCommerce().getCommerceId(), order.getCommerce().getCommerceId())) {
                            System.out.println("------------------ > 6");
                            GiftHistory giftHistory = new GiftHistory();
                            giftHistory.setCustomer(customer);
                            giftHistory.setProduct(gift);
                            giftHistory.setPurchaseDate(acquireDate);
                            System.out.println("------------------ > 7 bis : " + (earned - spent));
                            if ((earned - spent) >= gift.getRewardPointsPrice()) {
                                System.out.println("------------------ > 7 : " + (earned - spent));
                                PointTransaction pointTransaction = new PointTransaction();
                                pointTransaction.setAmount(gift.getRewardPointsPrice());
                                pointTransaction.setCommerce(gift.getCommerce());
                                pointTransaction.setType(TransactionType.SPENT);
                                pointTransaction.setTransactionDate(LocalDateTime.now());
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
