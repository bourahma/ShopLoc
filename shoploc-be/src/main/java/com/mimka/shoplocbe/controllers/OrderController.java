package com.mimka.shoplocbe.controllers;

import com.mimka.shoplocbe.dto.order.OrderDTO;
import com.mimka.shoplocbe.services.OrderService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/order")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/")
    @ResponseStatus(value = HttpStatus.CREATED)
    private OrderDTO createOrder (@RequestBody @Valid OrderDTO orderDTO, Principal principal) {
        return this.orderService.createOrder(principal.getName(), orderDTO);
    }

    @GetMapping("/{orderId}")
    private OrderDTO getOrder (@PathVariable long orderId, Principal principal) {
        return this.orderService.getOrder(principal.getName(), orderId);
    }

}
