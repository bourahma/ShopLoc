package com.mimka.shoplocbe.dto.order;

import com.mimka.shoplocbe.entities.Order;
import com.mimka.shoplocbe.entities.OrderProduct;
import com.mimka.shoplocbe.entities.OrderProductId;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class OrderDTOUtil {

    private final ModelMapper modelMapper;

    @Autowired
    public OrderDTOUtil(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;

        // Custom property mapping for Order to OrderDTO
        this.modelMapper.addMappings(new PropertyMap<Order, OrderDTO>() {
            @Override
            protected void configure() {
                map().setCommerceId(source.getCommerce().getCommerceId());
                map().setCommerceName(source.getCommerce().getCommerceName());
                map().setStatus(source.getOrderStatus());
                using(orderToOrderProductDTOConverter).map(source).setProducts(null);
            }
        });
    }

    // Converter from OrderProduct to OrderProductDTO
    private final Converter<Order, Set<OrderProductDTO>> orderToOrderProductDTOConverter = context -> {
        Order source = context.getSource();
        return source.getOrderProducts().stream()
                .map(orderProduct -> new OrderProductDTO(
                        orderProduct.getProduct().getProductId(),
                        orderProduct.getProduct().getProductName(),
                        orderProduct.getProduct().getPrice(),
                        orderProduct.getProduct().getRewardPointsPrice(),
                        orderProduct.getProduct().getPromotion() == null ? 0 : orderProduct.getProduct().getPromotion().getPromotionId(),
                        orderProduct.getQuantity()))
                .collect(Collectors.toSet());
    };

    // Method to convert Order to OrderDTO
    public OrderDTO toOrderDTO(Order order) {
        return modelMapper.map(order, OrderDTO.class);
    }

    public OrderProduct toOrderProduct (Long commerceId, OrderProductDTO orderProductDTO) {
        OrderProductId orderProductId = new OrderProductId(orderProductDTO.getProductId(), commerceId);
        return new OrderProduct(orderProductId, null, null, null, orderProductDTO.getQuantity(), orderProductDTO.getPrice());
    }
}