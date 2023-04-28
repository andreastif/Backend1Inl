package com.backend1inl.services.impl;



import com.backend1inl.domain.OrderItemDTO;
import com.backend1inl.domain.OrderItemEntity;
import com.backend1inl.exception.NoSuchCustomerException;
import com.backend1inl.repositories.ItemRepository;
import com.backend1inl.repositories.OrderItemRepository;
import com.backend1inl.repositories.OrderRepository;
import com.backend1inl.services.OrderService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    private final OrderItemRepository orderItemRepository;

    private final ItemRepository itemRepository;


    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository, OrderItemRepository orderItemRepository, ItemRepository itemRepository) {
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
        this.itemRepository = itemRepository;
    }

    @Override
    public OrderItemDTO addItemToOrder(Long orderId, Long itemId, int amount) {
        // TODO Skapa NoSuchOrderException + NoSuchItemException, så slipper vi ha isPresent()
        var matchItem = itemRepository.findById(itemId);
        var matchOrder = orderRepository.findById(orderId);

        if(matchOrder.isPresent() && matchItem.isPresent()) {

            // TODO TA BORT balance ?
            Long currentBalance = matchItem.get().getBalance();
            matchItem.get().setBalance(currentBalance - amount);


            itemRepository.save(matchItem.get());
            OrderItemEntity orderItem = OrderItemEntity.builder()
                    .orderEntity(matchOrder.get())
                    .itemEntity(matchItem.get())
                    .quantity(amount)
                    .build();

            // nytt Att Testa
            var savedOrderItem = orderItemRepository.save(orderItem);

            return OrderItemDTO.builder()
                    .id(savedOrderItem.getId())
                    .orderId(savedOrderItem.getOrderEntity().getId())
                    .itemId(savedOrderItem.getItemEntity().getId())
                    .quantity(savedOrderItem.getQuantity())
                    .build();
        }

        // Skall tas bort sedan när vi impl dem andra exceptions, behövs nu pga if-satsen isPresent()
        throw new NoSuchCustomerException("Random fel");
    }
}
