package com.backend1inl.services.impl;



import com.backend1inl.domain.*;
import com.backend1inl.exception.NoSuchItemException;
import com.backend1inl.exception.NoSuchOrderException;
import com.backend1inl.repositories.ItemRepository;
import com.backend1inl.repositories.OrderItemRepository;
import com.backend1inl.repositories.OrderRepository;
import com.backend1inl.services.ItemService;
import com.backend1inl.services.OrderService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    private final OrderItemRepository orderItemRepository;

    private final ItemService itemService;

    private final ItemRepository itemRepository;


    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository, OrderItemRepository orderItemRepository, ItemService itemService, ItemRepository itemRepository) {
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
        this.itemService = itemService;
        this.itemRepository = itemRepository;
    }

    @Override
    public OrderItemDTO addItemToOrder(Long orderId, Long itemId, int amount) {
        var matchItem = itemRepository.findById(itemId).orElseThrow( () -> new NoSuchItemException("No item with id: " + itemId + " found"));
        var matchOrder = orderRepository.findById(orderId).orElseThrow( () -> new NoSuchOrderException("No order with id: " + orderId + " found"));

            Long currentBalance = matchItem.getBalance();
            matchItem.setBalance(currentBalance - amount);

            itemRepository.save(matchItem);
            OrderItemEntity orderItem = OrderItemEntity.builder()
                    .orderEntity(matchOrder)
                    .itemEntity(matchItem)
                    .quantity(amount)
                    .build();

            var savedOrderItem = orderItemRepository.save(orderItem);

            return OrderItemDTO.builder()
                    .id(savedOrderItem.getId())
                    .orderId(savedOrderItem.getOrderEntity().getId())
                    .itemId(savedOrderItem.getItemEntity().getId())
                    .quantity(savedOrderItem.getQuantity())
                    .build();
        }

    @Override
    public List<OrderDTO> getAllOrders() {
        return orderRepository.findAll().stream()
                .map(orderEntity -> toDTO(orderEntity))
                .collect(Collectors.toList());
    }

    @Override
    public OrderDTO getItemsByOrderId(Long id) {
        var matchOrderEntity = orderRepository.findById(id)
                .orElseThrow( () -> new NoSuchOrderException("No order with id: " + id + " found"));

        return toDTO(matchOrderEntity);
    }


    private Item toItemDTO(ItemEntity itemEntity) {
        return itemService.itemEntityToItem(itemEntity);
    }

    private OrderDTO toDTO(OrderEntity orderEntity) {
        // All items related to order
        List<Item> itemsDTOList = orderEntity.getOrders()
                .stream()
                .map(orderItemEntity -> toItemDTO(orderItemEntity.getItemEntity()))
                .toList();

        return OrderDTO.builder()
                .id(orderEntity.getId())
                .lastUpdated(orderEntity.getLastUpdated())
                .created(orderEntity.getCreated())
                .items(itemsDTOList)
                .build();
    }

}

