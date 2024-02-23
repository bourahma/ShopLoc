package com.mimka.shoplocbe.controllers;

import com.mimka.shoplocbe.dto.order.OrderDTO;
import com.mimka.shoplocbe.exception.CommerceException;
import com.mimka.shoplocbe.facades.OrderFacade;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Map;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/order")
public class OrderController {

    private final OrderFacade orderFacade;

    public OrderController(OrderFacade orderFacade) {
        this.orderFacade = orderFacade;
    }

    @PostMapping("/")
    private OrderDTO createOrder (@RequestBody @Valid OrderDTO orderDTO, Principal principal) throws CommerceException {
        return this.orderFacade.createOrder(principal.getName(), orderDTO);
    }

    @GetMapping("/settle/using-balance/{orderId}")
    private OrderDTO settleOrderUsingBalance (@PathVariable long orderId, Principal principal) {
        return this.orderFacade.settleOrder(principal.getName(), orderId, false);
    }

    @GetMapping("/settle/using-points/{orderId}")
    private OrderDTO settleOrderUsingPoints (@PathVariable long orderId, Principal principal) {
        return this.orderFacade.settleOrder(principal.getName(), orderId, true);
    }

    @GetMapping("/qr-code-uuid/{orderId}")
    private Map<String,String> getQrCodeSettlePoints (@PathVariable long orderId, Principal principal) {
        return this.orderFacade.generateQrCode(orderId, principal);
    }

    @GetMapping("/settle/using-qr-code-points/{QRCodeUUID}")
    private Map<String,String> settleOrderUsingPointsQRCode (@PathVariable String QRCodeUUID) {
        return this.orderFacade.settleOrderUsingPointsQRCode(QRCodeUUID);
    }

    @GetMapping("/settle/using-qr-code-balance/{QRCodeUUID}")
    private Map<String,String> settleOrderUsingBalanceQRCode (@PathVariable String QRCodeUUID) {
        return this.orderFacade.settleOrderUsingBalanceQRCode(QRCodeUUID);
    }
}
