package com.backend1inl.controllers;

import com.backend1inl.domain.Customer;
import com.backend1inl.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

@Controller
public class CustomerController {

    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    //CREATE
    @PostMapping
    public ResponseEntity<List<Customer>> createCustomer(@Valid @RequestBody final Customer customer) {
        final Customer savedCustomer = customerService.create(customer);
        return new ResponseEntity<>(customerService.listCustomers(), HttpStatus.OK);
    }

    //READ
    @GetMapping
    public ResponseEntity<List<Customer>> listCustomers(){
        return new ResponseEntity<>(customerService.listCustomers(),HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<Customer> retrieveCustomer(@PathVariable final Long id){
        final Customer foundCustomer = customerService.findCustomerById(id);
        return new ResponseEntity<>(foundCustomer, HttpStatus.OK);
    }

    //UPDATE

    //DELETE
}
