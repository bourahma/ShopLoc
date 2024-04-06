package com.mimka.shoplocbe.controllers;

import com.mimka.shoplocbe.dto.order.OrderDTO;
import com.mimka.shoplocbe.exception.CommerceNotFoundException;
import com.mimka.shoplocbe.exception.InsufficientFundsException;
import com.mimka.shoplocbe.facades.OrderFacade;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Map;

@RestController
@RequestMapping("/order")
@CrossOrigin(origins = "${allowed.origin}")
@PreAuthorize("hasAuthority('SCOPE_CUSTOMER')")
public class OrderController {

    private final OrderFacade orderFacade;

    @Autowired
    public OrderController(OrderFacade orderFacade) {
        this.orderFacade = orderFacade;
    }

    @PostMapping("/")
    public OrderDTO createOrder (@RequestBody @Valid OrderDTO orderDTO, Principal principal) throws CommerceNotFoundException {
        return this.orderFacade.createOrder(principal.getName(), orderDTO);
    }

    @GetMapping("/settle/using-balance/{orderId}")
    public OrderDTO settleOrderUsingBalance (@PathVariable long orderId, Principal principal) throws InsufficientFundsException {
        return this.orderFacade.settleOrder(principal.getName(), orderId, false);
    }

    @GetMapping("/settle/using-points/{orderId}")
    public OrderDTO settleOrderUsingPoints (@PathVariable long orderId, Principal principal) throws InsufficientFundsException {
        return this.orderFacade.settleOrder(principal.getName(), orderId, true);
    }

    @GetMapping("/qr-code-uuid/{orderId}")
    public Map<String,String> getQrCodeSettle (@PathVariable long orderId, Principal principal) {
        return this.orderFacade.generateQrCode(orderId, principal);
    }

    @GetMapping("/settle/using-qr-code-points/{qRCodeUUID}")
    public Map<String,String> settleOrderUsingPointsQRCode (@PathVariable String qRCodeUUID) throws InsufficientFundsException {
        return this.orderFacade.settleOrderUsingPointsQRCode(qRCodeUUID);
    }

    @GetMapping("/settle/using-qr-code-balance/{qRCodeUUID}")
    public Map<String,String> settleOrderUsingBalanceQRCode (@PathVariable String qRCodeUUID) throws InsufficientFundsException {
        return this.orderFacade.settleOrderUsingBalanceQRCode(qRCodeUUID);
    }
}
