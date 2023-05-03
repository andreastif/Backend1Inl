package com.backend1inl.services;



import com.backend1inl.domain.OrderDTO;
import com.backend1inl.domain.OrderItemDTO;

import java.util.List;

public interface OrderService {
    OrderItemDTO addItemToOrder(Long orderId, Long itemId, int quantity);

    List<OrderDTO> getAllOrders();

    OrderDTO getItemsByOrderId(Long id);

    List<OrderDTO> getOrdersByCustomerId(Long id);
}
