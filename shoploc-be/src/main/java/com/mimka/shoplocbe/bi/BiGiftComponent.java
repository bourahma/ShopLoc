package com.mimka.shoplocbe.bi;

import com.mimka.shoplocbe.entities.*;
import com.mimka.shoplocbe.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Component
public class BiGiftComponent {
    private final GiftHistoryRepository giftHistoryRepository;
    private final ProductRepository productRepository;
    private final CustomerRepository customerRepository;
    private final OrderRepository orderRepository;
    private final VfpHistoryRepository vfpHistoryRepository;


    @Autowired
    public BiGiftComponent(GiftHistoryRepository giftHistoryRepository,
                           ProductRepository productRepository,
                           CustomerRepository customerRepository,
                           OrderRepository orderRepository,
                           VfpHistoryRepository vfpHistoryRepository) {
        this.giftHistoryRepository = giftHistoryRepository;
        this.productRepository = productRepository;
        this.customerRepository = customerRepository;
        this.orderRepository = orderRepository;
        this.vfpHistoryRepository = vfpHistoryRepository;
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
                processDate = processDate.plusDays(3);
            }
        }
    }

    private void acquireGift (Customer customer, LocalDate acquireDate) {
        List<Product> gifts = productRepository.findByGiftIsTrue();
        List<VfpHistory> vfpHistories = vfpHistoryRepository.findByCustomer(customer);
        List<Order> customerOrders = orderRepository.findByCustomerAndAndOrderDateAfterAndAndOrderStatus(customer, acquireDate, OrderStatus.PAID.name());

        for (VfpHistory vfpHistory : vfpHistories) {
            if (acquireDate.isAfter(vfpHistory.getGrantedDate()) && acquireDate.isBefore(vfpHistory.getExpirationDate())) {
                for (Product gift : gifts) {
                    for (Order order : customerOrders) {
                        if (Objects.equals(gift.getCommerce().getCommerceId(), order.getCommerce().getCommerceId())) {
                            GiftHistory giftHistory = new GiftHistory();
                            giftHistory.setCustomer(customer);
                            giftHistory.setProduct(gift);
                            giftHistory.setPurchaseDate(acquireDate);
                            //if (customer.getFidelityCard().getPoints() >= gift.getRewardPointsPrice()) {
                            System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
                            giftHistoryRepository.save(giftHistory);
                            break;
                            //}
                        }
                    }
                }
            }
        }
    }
}
