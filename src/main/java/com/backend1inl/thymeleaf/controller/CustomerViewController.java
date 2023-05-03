package com.backend1inl.thymeleaf.controller;

import com.backend1inl.services.CustomerService;
import com.backend1inl.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("thyme/customers")
public class CustomerViewController {
    private final CustomerService customerService;

    @Autowired
    public CustomerViewController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    public String allCustomersView(Model model) {
        var allCustomers = customerService.listCustomers();
        model.addAttribute("customers", allCustomers);
        return "customers";
    }

}
