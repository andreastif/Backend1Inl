package com.backend1inl.controllers;

import com.backend1inl.domain.Customer;
import com.backend1inl.services.CustomerService;
import com.backend1inl.utils.DeleteResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("customers")
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
    public ResponseEntity<List<Customer>> listCustomers() {
        return new ResponseEntity<>(customerService.listCustomers(), HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<EntityModel<Customer>> retrieveCustomer(@PathVariable final Long id) {
        final Customer foundCustomer = customerService.findCustomerById(id);
        return new ResponseEntity<>(foundCustomer, HttpStatus.OK);
    }

    //UPDATE

    //DELETE
    @DeleteMapping("{id}")
    public ResponseEntity<DeleteResponse> deleteCustomerById(@PathVariable Long id) {
        boolean deleted = customerService.deleteCustomerById(id);
        return new ResponseEntity<>(new DeleteResponse(deleted), HttpStatus.OK);
    }

}
