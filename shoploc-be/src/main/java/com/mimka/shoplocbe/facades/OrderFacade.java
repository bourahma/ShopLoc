package com.mimka.shoplocbe.facades;

import com.mimka.shoplocbe.dto.order.OrderDTO;
import com.mimka.shoplocbe.exception.CommerceNotFoundException;
import com.mimka.shoplocbe.exception.InsufficientFundsException;

import java.security.Principal;
import java.util.Map;


public interface OrderFacade {

    OrderDTO createOrder (String customerUsername, OrderDTO orderDTO) throws CommerceNotFoundException;

    OrderDTO settleOrder(String customerUsername, Long orderId, boolean usingPoints) throws InsufficientFundsException;

    Map<String,String> generateQrCode (long orderId, Principal principal);

    Map<String,String> settleOrderUsingPointsQRCode (String qRCodeUUID) throws InsufficientFundsException;

    Map<String,String> settleOrderUsingBalanceQRCode (String qRCodeUUID) throws InsufficientFundsException;
}
