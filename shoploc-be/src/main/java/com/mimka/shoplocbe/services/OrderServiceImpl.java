package com.mimka.shoplocbe.services;

import com.mimka.shoplocbe.dto.order.OrderDTO;
import com.mimka.shoplocbe.dto.order.OrderDTOUtil;
import com.mimka.shoplocbe.dto.order.OrderProductDTO;
import com.mimka.shoplocbe.entities.*;
import com.mimka.shoplocbe.exception.CommerceNotFoundException;
import com.mimka.shoplocbe.exception.ProductException;
import com.mimka.shoplocbe.repositories.*;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final CommerceService commerceService;

    private final MailServiceImpl mailService;

    private final OrderDTOUtil orderDTOUtil;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository,
                            CommerceService commerceService,
                            OrderDTOUtil orderDTOUtil,
                            ProductRepository productRepository, MailServiceImpl mailService) {
        this.orderRepository = orderRepository;
        this.commerceService = commerceService;
        this.orderDTOUtil = orderDTOUtil;
        this.productRepository = productRepository;
        this.mailService = mailService;
    }

    @Override
    public Order createOrder(Customer customer, OrderDTO orderDTO) throws CommerceNotFoundException {
        Order order = new Order();

        order.setCustomer(customer);
        order.setCommerce(this.commerceService.getCommerce(orderDTO.getCommerceId()));
        order.setOrderDate(LocalDate.now());
        order.setOrderStatus(OrderStatus.PENDING.name());

        Set<OrderProduct> orderProducts = new HashSet<>();
        for (OrderProductDTO orderProductDTO : orderDTO.getProducts()) {
            OrderProduct orderProduct = this.orderDTOUtil.toOrderProduct(order.getCommerce().getCommerceId(), orderProductDTO);
            orderProduct.setOrder(order);
            orderProduct.setProduct(this.productRepository.findById(orderProductDTO.getProductId()).get());
            orderProducts.add(orderProduct);
        }
        order.setOrderProducts(orderProducts);
        this.orderRepository.save(order);

        return order;
    }

    @Override
    public Order getOrder(String principal, long orderId) {
        return this.orderRepository.findById(orderId).orElse(null);
    }

    @Override
    public Order getOrder(long orderId) {
        return this.orderRepository.findById(orderId).get();
    }

    @Override
    public Order payOrder(Long orderId) throws ProductException {
        Order order = this.orderRepository.findById(orderId)
                .orElseThrow(() -> new ProductException("Commande avec l'ID " + orderId + " non trouvée."));

        if (order.getOrderStatus().equals(OrderStatus.PENDING.name())) {
            processOrder(order);
        }

        return order;
    }

    private void processOrder(Order order) throws ProductException {
        for (OrderProduct orderProduct : order.getOrderProducts()) {
            Product product = orderProduct.getProduct();
            Promotion promotion = product.getPromotion();

            if (product.getQuantity() - orderProduct.getQuantity() < 0) {
                throw new ProductException("La quantité disponible pour le produit '" + product.getProductName() +
                        "' est insuffisante pour compléter la commande. Quantité disponible: " +
                        product.getQuantity() + ".");
            }

            notifyMerchantsIfProductOutOfStock(product, orderProduct.getQuantity(), new ArrayList<>(order.getCommerce().getMerchants()));

            updateProductAndOrderProduct(product, orderProduct, promotion);

            product.setQuantity(product.getQuantity() - orderProduct.getQuantity());
        }

        order.setOrderStatus(OrderStatus.PAID.name());
        this.orderRepository.save(order);
    }

    private void notifyMerchantsIfProductOutOfStock(Product product, int orderProductQuantity, List<Merchant> merchants) {
        if (product.getQuantity() == 0 || product.getQuantity() - orderProductQuantity == 0) {
            try {
                this.mailService.triggerMerchantsProductOutOfStock(product, merchants);
            } catch (MessagingException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void updateProductAndOrderProduct(Product product, OrderProduct orderProduct, Promotion promotion) {
        if (promotion == null) {
            this.productRepository.save(product);
        } else if (promotion.getPromotionType().equals(PromotionType.OFFER.name())) {
            orderProduct.setPurchasePrice(product.getPrice());
            orderProduct.setPromotion(promotion);

            this.productRepository.save(product);
        } else if (promotion.getPromotionType().equals(PromotionType.DISCOUNT.name())) {
            orderProduct.setPromotion(promotion);

            this.productRepository.save(product);
        }
    }


    @Override
    public double getOrderTotalPrice(Long orderId, boolean usingPoints) {
        return this.getOrderTotal(orderId, usingPoints);
    }

    public double getOrderTotal(Long orderId, boolean usePointsPrice) {
        Order order = orderRepository.findByOrderIdAndOrderStatus(orderId, OrderStatus.PENDING.name());
        if (order == null) {
            return 0.0;
        }

        double totalOrderPrice = 0.0;
        for (OrderProduct orderProduct : order.getOrderProducts()) {
            double productPrice = usePointsPrice ? orderProduct.getProduct().getRewardPointsPrice() : orderProduct.getProduct().getPrice();
            Promotion promotion = orderProduct.getProduct().getPromotion();
            if (promotion == null) {
                totalOrderPrice += orderProduct.getQuantity() * productPrice;
            }
            else if (promotion.getPromotionType().equals(PromotionType.DISCOUNT.name())) {
                totalOrderPrice += orderProduct.getQuantity() * productPrice * (1 - (promotion.getDiscountPercent() / 100.0));
            }
            else if (promotion.getPromotionType().equals(PromotionType.OFFER.name())) {
                DecimalFormat df = new DecimalFormat("#.##");
                totalOrderPrice += Double.parseDouble(df.format(orderProduct.getQuantity() * productPrice));
                int additionalQuantity = (int) Math.floor(((double) (orderProduct.getQuantity() * (promotion.getOfferedItems())) / (promotion.getRequiredItems())));
                orderProduct.setQuantity(orderProduct.getQuantity() + additionalQuantity);
            }
        }
        System.out.println("total : " + totalOrderPrice);
        return totalOrderPrice;
    }
}
