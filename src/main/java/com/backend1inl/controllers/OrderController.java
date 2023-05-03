package com.backend1inl.controllers;



import com.backend1inl.domain.Customer;
import com.backend1inl.domain.OrderDTO;
import com.backend1inl.domain.OrderItemDTO;
import com.backend1inl.services.CustomerService;
import com.backend1inl.services.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("orders")
@Slf4j
public class OrderController {

    private final OrderService orderService;
    private final CustomerService customerService;

    @Autowired
    public OrderController(OrderService orderService, CustomerService customerService) {
        this.orderService = orderService;
        this.customerService = customerService;
    }


    // Lägga till Produkt på bef order
    // tex  .../orders/buy?itemId=10004&orderId=10001&quantity=3
    @PostMapping("buy")
    public ResponseEntity<OrderItemDTO> addItemToOrder(@RequestParam Long itemId, @RequestParam Long orderId, @RequestParam int quantity) {
        OrderItemDTO savedOrderItemDTO = orderService.addItemToOrder(orderId, itemId, quantity);

        log.info("Added item {} to order {}", itemId, orderId);
        return new ResponseEntity<>(savedOrderItemDTO, HttpStatus.CREATED);
    }

    // Hämta Alla orders i DB
    @GetMapping
    public ResponseEntity<List<OrderDTO>> getAllOrders() {
        List<OrderDTO> allOrders = orderService.getAllOrders();
        log.info("Showing all, {}x orders in db", allOrders.size());

        return new ResponseEntity<>(allOrders, HttpStatus.OK);
    }

    // Hämta Alla Items för ett visst order Id
    @GetMapping("{id}")
    public ResponseEntity<OrderDTO> getItemsByOrderId(@PathVariable Long id) {
        OrderDTO order = orderService.getItemsByOrderId(id);
        log.info("Showing all items on order: {}", id);

        return new ResponseEntity<>(order, HttpStatus.OK);
    }


    @GetMapping("customer/{id}")
    public ResponseEntity<List<OrderDTO>> getOrdersByCustomerId(@PathVariable Long id) {
        customerService.findCustomerById(id);
        List<OrderDTO> orderList = orderService.getOrdersByCustomerId(id);

        return new ResponseEntity<>(orderList, HttpStatus.OK);

    }

}
