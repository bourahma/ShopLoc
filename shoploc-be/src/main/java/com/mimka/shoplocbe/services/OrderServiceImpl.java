package com.mimka.shoplocbe.services;

import com.mimka.shoplocbe.dto.order.OrderDTO;
import com.mimka.shoplocbe.dto.order.OrderDTOUtil;
import com.mimka.shoplocbe.dto.order.OrderProductDTO;
import com.mimka.shoplocbe.entities.*;
import com.mimka.shoplocbe.exception.CommerceNotFoundException;
import com.mimka.shoplocbe.repositories.OrderRepository;
import com.mimka.shoplocbe.repositories.ProductRepository;
import com.mimka.shoplocbe.repositories.PromotionHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final CommerceService commerceService;

    private final PromotionHistoryRepository promotionHistoryRepository;
    private final OrderDTOUtil orderDTOUtil;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository, CommerceService commerceService, OrderDTOUtil orderDTOUtil, ProductRepository productRepository, PromotionHistoryRepository promotionHistoryRepository) {
        this.orderRepository = orderRepository;
        this.commerceService = commerceService;
        this.orderDTOUtil = orderDTOUtil;
        this.productRepository = productRepository;
        this.promotionHistoryRepository = promotionHistoryRepository;
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
    public Order payOrder(Long orderId) {
        Optional<Order> optionalOrder = this.orderRepository.findById(orderId);
        optionalOrder.ifPresent(order -> {
            if (order.getOrderStatus().equals(OrderStatus.PENDING.name())) {
                for (OrderProduct orderProduct : order.getOrderProducts()) {
                    Product product = orderProduct.getProduct();
                    Promotion promotion = product.getPromotion();

                    if (promotion instanceof OfferPromotion && promotion != null) {
                        int quantity = (int) Math.floor((double) (orderProduct.getQuantity() * ((OfferPromotion) promotion).getOfferedItems()) / ((OfferPromotion) promotion).getRequiredItems());
                        orderProduct.setQuantity(orderProduct.getQuantity() + quantity);
                        orderProduct.setPurchasePrice(product.getPrice());
                        product.setQuantity(product.getQuantity() - orderProduct.getQuantity());
                        this.productRepository.save(product);
                    }
                    if (promotion != null) {
                        PromotionHistory promotionHistory = new PromotionHistory();
                        promotionHistory.setPromotionHistoryId(promotion.getPromotionId());
                        promotionHistory.setDescription(promotion.getDescription());
                        promotionHistory.setProduct(product);
                        promotionHistory.setCommerce(promotion.getCommerce());
                        promotionHistory.setEndDate(promotion.getEndDate());
                        promotionHistory.setDiscountPercent(10);//promotion.getDiscountPercent());
                        promotionHistory.setOfferedItems(10);//(promotion.getOfferedItems());
                        promotionHistory.setRequiredItems(10);//(promotion.getRequiredItems());

                        this.promotionHistoryRepository.save(promotionHistory);
                    }
                }
                order.setOrderStatus(OrderStatus.PAID.name());
                this.orderRepository.save(order);
            }
        });
        return optionalOrder.orElse(null);
    }

    @Override
    public double getOrderTotalPrice(Long orderId, boolean usingPoints) {
        return this.getOrderTotal(orderId, usingPoints);
    }

    public double getOrderTotal(Long orderId, boolean usePointsPrice) {
        Order order = this.orderRepository.findById(orderId).orElse(null);

        if (order == null || !order.getOrderStatus().equals(OrderStatus.PENDING.name())) {
            return 0.0;
        }

        double totalOrderPrice = 0.0;

        for (OrderProduct orderProduct : order.getOrderProducts()) {
            Product product = orderProduct.getProduct();
            Promotion promotion = product.getPromotion();

            double productPrice = 0.0;

            if (promotion instanceof DiscountPromotion && promotion != null) {
                productPrice = usePointsPrice ? orderProduct.getProduct().getRewardPointsPrice()
                        : orderProduct.getProduct().getPrice();
                totalOrderPrice += orderProduct.getQuantity() * (productPrice * ((double) ((DiscountPromotion) promotion).getDiscountPercent() / 100));

            } else if (promotion != null && promotion instanceof OfferPromotion && orderProduct.getQuantity() >= ((OfferPromotion) promotion).getRequiredItems()) {
                productPrice = usePointsPrice ? orderProduct.getProduct().getRewardPointsPrice()
                        : orderProduct.getProduct().getPrice();
                totalOrderPrice += orderProduct.getQuantity() * productPrice;

                int quantity = (int) Math.floor((double) (orderProduct.getQuantity() * ((OfferPromotion) promotion).getOfferedItems()) / ((OfferPromotion) promotion).getRequiredItems());
                orderProduct.setQuantity(orderProduct.getQuantity() + quantity);
            } else {
                productPrice = usePointsPrice ? orderProduct.getProduct().getRewardPointsPrice()
                        : orderProduct.getProduct().getPrice();
                totalOrderPrice += orderProduct.getQuantity() * productPrice;
            }
        }

        return totalOrderPrice;
    }
}
