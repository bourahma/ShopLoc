package com.mimka.shoplocbe.facades;

import com.mimka.shoplocbe.dto.order.OrderDTO;
import com.mimka.shoplocbe.exception.CommerceException;

import java.security.Principal;
import java.util.Map;


public interface OrderFacade {

    OrderDTO createOrder (String customerUsername, OrderDTO orderDTO) throws CommerceException;

    OrderDTO settleOrder(String customerUsername, Long orderId, boolean usingPoints);

    Map<String,String> generateQrCode (long orderId, Principal principal);

    Map<String,String> settleOrderUsingPointsQRCode (String QRCodeUUID);

    Map<String,String> settleOrderUsingBalanceQRCode (String QRCodeUUID);
}
