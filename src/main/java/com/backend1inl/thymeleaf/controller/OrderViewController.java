package com.backend1inl.thymeleaf.controller;

import com.backend1inl.domain.Item;
import com.backend1inl.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("thyme/orders")
public class OrderViewController {

    private final OrderService orderService;

    @Autowired
    public OrderViewController(OrderService orderService) {
        this.orderService = orderService;
    }


    @GetMapping
    public String allOrdersView(Model model) {
        var allOrders = orderService.getAllOrders();
        model.addAttribute("orders", allOrders);
        return "orders";
    }

    @GetMapping("{id}")
    public String viewOrderByID(@PathVariable Long id, Model model) {
        var order = orderService.getItemsByOrderId(id);
        List<Item> allItems = order.getItems();
        boolean hasItems = allItems.size() > 0;

        model.addAttribute("order", order);
        model.addAttribute("items", allItems);
        model.addAttribute("hasItems", hasItems);
        return "order-item";
    }
}
