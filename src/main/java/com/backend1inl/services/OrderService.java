package com.backend1inl.services;


import com.backend1inl.domain.OrderItemDTO;

public interface OrderService {
    OrderItemDTO addItemToOrder(Long orderId, Long itemId, int quantity);
}
