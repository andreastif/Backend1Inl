package com.backend1inl.controllers;


import com.backend1inl.domain.OrderItemDTO;
import com.backend1inl.services.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("orders")
@Slf4j
public class OrderController {

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("buy")
    public ResponseEntity<OrderItemDTO> addItemToOrder(@RequestParam Long itemId, @RequestParam Long orderId, @RequestParam int quantity) {
        OrderItemDTO savedOrderItemDTO = orderService.addItemToOrder(orderId, itemId, quantity);

        return new ResponseEntity<>(savedOrderItemDTO, HttpStatus.CREATED);
    }

    // Hämta Alla Ordrar för ett visst order Id

    // Hämta ALLA Ordrar

    // Ta bort Order By Id
}
