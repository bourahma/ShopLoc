package com.mimka.shoplocbe.bi;

import com.mimka.shoplocbe.entities.*;
import com.mimka.shoplocbe.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@Component
public class BiOrderComponent {
    private final CustomerRepository customerRepository;
    private final CommerceRepository commerceRepository;
    private final VfpHistoryRepository vfpHistoryRepository;
    private final OrderRepository orderRepository;
    private final OrderProductRepository orderProductRepository;
    private final PointTransactionRepository pointTransactionRepository;
    private final BenefitHistoryRepository benefitHistoryRepository;
    private final BenefitRepository benefitRepository;
    private final GiftHistoryRepository giftHistoryRepository;
    private final Random random = new Random();
    @Autowired
    public BiOrderComponent(CustomerRepository customerRepository,
                            CommerceRepository commerceRepository,
                            VfpHistoryRepository vfpHistoryRepository,
                            OrderRepository orderRepository,
                            OrderProductRepository orderProductRepository,
                            PointTransactionRepository pointTransactionRepository,
                            BenefitHistoryRepository benefitHistoryRepository,
                            BenefitRepository benefitRepository,
                            GiftHistoryRepository giftHistoryRepository)
    {
        this.customerRepository = customerRepository;
        this.commerceRepository = commerceRepository;
        this.vfpHistoryRepository = vfpHistoryRepository;
        this.orderRepository = orderRepository;
        this.orderProductRepository = orderProductRepository;
        this.pointTransactionRepository = pointTransactionRepository;
        this.benefitHistoryRepository = benefitHistoryRepository;
        this.benefitRepository = benefitRepository;
        this.giftHistoryRepository = giftHistoryRepository;
    }

    public void process () {
        List<Customer> customers = customerRepository.findAll();
        List<Commerce> commerces = commerceRepository.findAll();

        for (Customer customer : customers) {
            if (!customer.getEnabled()) {
                continue;
            }

            LocalDate processDate = customer.getSubscriptionDate();
            LocalDate today = LocalDate.now();

            while (processDate.isBefore(today)) {
                int action = random.nextInt(3);

                if (action == 2) {
                    processSingleOrder(customer, commerces, processDate);
                } else if (action == 1 && random.nextInt(2) == 1) {
                    processMultipleOrders(customer, commerces, processDate);
                }

                processDate = processDate.plusDays(7);
            }
        }
    }

    private void processSingleOrder(Customer customer, List<Commerce> commerces, LocalDate orderDate) {
        Commerce commerce = selectRandomCommerce(commerces);
        createAndSaveOrder(customer, commerce, orderDate, 1);
    }

    private void processMultipleOrders(Customer customer, List<Commerce> commerces, LocalDate startDate) {
        Commerce commerce = selectRandomCommerce(commerces);
        // First order
        createAndSaveOrder(customer, commerce, startDate, 1);
        // Second order, next day
        createAndSaveOrder(customer, commerce, startDate.plusDays(1), 1);
        // Third order, two days after the first
        createAndSaveOrder(customer, commerce, startDate.plusDays(2), 1);
        // VfpHistory, three days after the first
        saveVfpHistory(customer, startDate.plusDays(3));

        Random random = new Random();
        LocalDate checkDate = startDate.plusDays(3);

        while (checkDate.isBefore(startDate.plusDays(7))) {
            int randomProduct = random.nextInt(2);
            if (randomProduct == 0) {
                acquireBenefit(customer, checkDate.plusDays(1));
            }
            checkDate = checkDate.plusDays(1);
        }
    }

    private Commerce selectRandomCommerce(List<Commerce> commerces) {
        return commerces.get(random.nextInt(commerces.size()));
    }

    private void createAndSaveOrder(Customer customer, Commerce commerce, LocalDate orderDate, int orderCount) {
        double totalOrder = 0;
        if (orderCount > 0) {
            Order order = new Order();
            order.setCustomer(customer);
            order.setOrderDate(orderDate);
            order.setOrderStatus(OrderStatus.PAID.name());
            order.setCommerce(commerce);
            order = orderRepository.save(order);

            for (int i = 0; i < orderCount; i++) {
                if (commerce.getProducts().isEmpty()) {
                    continue;
                }
                int randomProduct = random.nextInt(commerce.getProducts().size());
                Product product = commerce.getProducts().get(randomProduct == 0 ? 1 : randomProduct);
                OrderProduct orderProduct = new OrderProduct();
                orderProduct.setProduct(product);
                orderProduct.setOrder(order);

                OrderProductId orderProductId = new OrderProductId(order.getOrderId(), product.getProductId());
                orderProduct.setId(orderProductId);
                totalOrder += setOrderProductQuantity(product, orderProduct);
                orderProductRepository.save(orderProduct);
            }

            this.savePointTransaction(customer, commerce, LocalDateTime.now(), totalOrder);
        }
    }

    private double setOrderProductQuantity(Product product, OrderProduct orderProduct) {
        int quantity = product.getPrice() > 100.00 ? random.nextInt(2) + 1 : random.nextInt(10) + 1;
        orderProduct.setQuantity(quantity);
        return quantity * product.getPrice();
    }

    private void saveVfpHistory(Customer customer, LocalDate grantedDate) {
        VfpHistory vfpHistory = new VfpHistory();
        vfpHistory.setGrantedDate(grantedDate);
        vfpHistory.setCustomer(customer);
        vfpHistory.setExpirationDate(grantedDate.plusDays(7));

        vfpHistoryRepository.save(vfpHistory);
    }

    private void savePointTransaction (Customer customer, Commerce commerce, LocalDateTime earnedDate, double amount) {
        PointTransaction pointTransaction = new PointTransaction();
        FidelityCard fidelityCard = customer.getFidelityCard();
        pointTransaction.setFidelityCard(fidelityCard);
        pointTransaction.setCommerce(commerce);
        pointTransaction.setType(TransactionType.EARNED);
        pointTransaction.setTransactionDate(earnedDate);
        pointTransaction.setAmount((int) amount);

        pointTransactionRepository.save(pointTransaction);
    }

    private void acquireBenefit (Customer customer, LocalDate acquisitionDate) {
        List<Benefit> benefits = benefitRepository.findAll();
        Benefit benefit = benefits.get(random.nextInt(benefits.size()));
        BenefitHistory benefitHistory = new BenefitHistory();
        benefitHistory.setBenefit(benefit);
        benefitHistory.setAcquisitionDate(acquisitionDate);
        benefitHistory.setCustomer(customer);
        benefitHistory.setLicensePlateNumber(null);
        benefitHistory.setAcquisitionTime(null);
        benefitHistory.setQrCode(UUID.randomUUID().toString());

        benefitHistoryRepository.save(benefitHistory);
    }

}
