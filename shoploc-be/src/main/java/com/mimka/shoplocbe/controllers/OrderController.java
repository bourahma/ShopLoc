package com.mimka.shoplocbe.controllers;

import com.mimka.shoplocbe.dto.order.OrderDTO;
import com.mimka.shoplocbe.exception.CommerceNotFoundException;
import com.mimka.shoplocbe.facades.OrderFacade;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Map;

@CrossOrigin(origins = "${allowed.origin}")
@RestController
@RequestMapping("/order")
public class OrderController {

    private final OrderFacade orderFacade;

    public OrderController(OrderFacade orderFacade) {
        this.orderFacade = orderFacade;
    }

    @PostMapping("/")
    public OrderDTO createOrder (@RequestBody @Valid OrderDTO orderDTO, Principal principal) throws CommerceNotFoundException {
        return this.orderFacade.createOrder(principal.getName(), orderDTO);
    }

    @GetMapping("/settle/using-balance/{orderId}")
    public OrderDTO settleOrderUsingBalance (@PathVariable long orderId, Principal principal) {
        return this.orderFacade.settleOrder(principal.getName(), orderId, false);
    }

    @GetMapping("/settle/using-points/{orderId}")
    public OrderDTO settleOrderUsingPoints (@PathVariable long orderId, Principal principal) {
        return this.orderFacade.settleOrder(principal.getName(), orderId, true);
    }

    @GetMapping("/qr-code-uuid/{orderId}")
    public Map<String,String> getQrCodeSettle (@PathVariable long orderId, Principal principal) {
        return this.orderFacade.generateQrCode(orderId, principal);
    }

    @GetMapping("/settle/using-qr-code-points/{QRCodeUUID}")
    public Map<String,String> settleOrderUsingPointsQRCode (@PathVariable String QRCodeUUID) {
        return this.orderFacade.settleOrderUsingPointsQRCode(QRCodeUUID);
    }

    @GetMapping("/settle/using-qr-code-balance/{QRCodeUUID}")
    public Map<String,String> settleOrderUsingBalanceQRCode (@PathVariable String QRCodeUUID) {
        return this.orderFacade.settleOrderUsingBalanceQRCode(QRCodeUUID);
    }
}
