package com.backend1inl.services.impl;


import com.backend1inl.domain.Order;
import com.backend1inl.domain.OrderEntity;
import com.backend1inl.repositories.OrderRepository;
import com.backend1inl.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }


    @Override
    public List<Order> getAllOrders() {
        return orderRepository.findAll().stream()
                .map(orderEntity -> toOrder(orderEntity))
                .collect(Collectors.toList());
    }


    private Order toOrder(OrderEntity orderEntity) {
        return Order.builder()
                .id(orderEntity.getId())
                .created(orderEntity.getCreated())
                .lastUpdated(orderEntity.getLastUpdated())
                .build();
    }

    private OrderEntity toOrderEntity(Order order) {
        return OrderEntity.builder()
                .id(order.getId())
                .created(order.getCreated())
                .lastUpdated(order.getLastUpdated())
                .build();
    }
}
