package com.mimka.shoplocbe.facades;

import com.mimka.shoplocbe.dto.order.OrderDTO;
import com.mimka.shoplocbe.exception.CommerceNotFoundException;
import com.mimka.shoplocbe.exception.InsufficientFundsException;
import com.mimka.shoplocbe.exception.ProductException;

import java.security.Principal;
import java.util.Map;


public interface OrderFacade {

    OrderDTO createOrder (String customerUsername, OrderDTO orderDTO) throws CommerceNotFoundException;

    OrderDTO settleOrder(String customerUsername, Long orderId, boolean usingPoints) throws InsufficientFundsException, ProductException;

    Map<String,String> generateQrCode (long orderId, Principal principal);

    Map<String,String> settleOrderUsingPointsQRCode (String qRCodeUUID) throws InsufficientFundsException, ProductException;

    Map<String,String> settleOrderUsingBalanceQRCode (String qRCodeUUID) throws InsufficientFundsException, ProductException;
}
