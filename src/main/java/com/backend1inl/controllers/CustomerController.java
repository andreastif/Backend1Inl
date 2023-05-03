package com.backend1inl.controllers;

import com.backend1inl.domain.Customer;
import com.backend1inl.services.CustomerService;
import com.backend1inl.utils.DeleteResponse;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("customers")
@Slf4j // Logging
public class CustomerController {

    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    //CREATE
    @PostMapping
    public ResponseEntity<EntityModel<Customer>> createCustomer(@Valid @RequestBody final Customer customer) {
        final Customer savedCustomer = customerService.create(customer);

        // links HATEOAS
        EntityModel<Customer> entityModel = EntityModel.of(savedCustomer,
                linkTo(methodOn(CustomerController.class).retrieveCustomer(savedCustomer.getId())).withSelfRel(),
                linkTo(methodOn(CustomerController.class).listCustomers()).withRel("all-customers"));

        log.info("POST: Created new customer with id {}", savedCustomer.getId());

        return new ResponseEntity<>(entityModel, HttpStatus.CREATED);
    }

    //READ
    @GetMapping
    public ResponseEntity<List<Customer>> listCustomers() {

        log.info("GET: All customers listed");

        return new ResponseEntity<>(customerService.listCustomers(), HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<EntityModel<Customer>> retrieveCustomer(@PathVariable final Long id) {
        final Customer foundCustomer = customerService.findCustomerById(id);


        // Hateoas links
        EntityModel<Customer> entityModel = EntityModel.of(foundCustomer,
                linkTo(methodOn(CustomerController.class).retrieveCustomer(foundCustomer.getId())).withSelfRel(),
                linkTo(methodOn(CustomerController.class).listCustomers()).withRel("all-customers"));

        log.info("GET: Customer with id: " + id);

        return new ResponseEntity<>(entityModel, HttpStatus.OK);
    }

    //UPDATE
    @PutMapping("{id}")
    public ResponseEntity<EntityModel<Customer>> updateList(@Valid @RequestBody Customer customer, @PathVariable Long id) {

        customer.setId(id);
        final boolean exists = customerService.doesCustomerExist(customer);
        final Customer savedCustomer = customerService.save(customer);

        EntityModel<Customer> entityModel = EntityModel.of(savedCustomer, linkTo(methodOn(CustomerController.class).retrieveCustomer(savedCustomer.getId())).withSelfRel(),
                linkTo(methodOn(CustomerController.class).listCustomers()).withRel("all-lists"));

        if (exists) {
            log.info("UPDATED: customer: {}", savedCustomer);
            return new ResponseEntity<>(entityModel, HttpStatus.OK);
        }

        log.info("POST: customer: {}", savedCustomer);
        return new ResponseEntity<>(entityModel, HttpStatus.CREATED);
    }

    //DELETE
    @DeleteMapping("{id}")
    public ResponseEntity<DeleteResponse> deleteCustomerById(@PathVariable Long id) {
        boolean deleted = customerService.deleteCustomerById(id);

        log.info("DELETE: Customer with id: " + id);
        return new ResponseEntity<>(new DeleteResponse(deleted), HttpStatus.OK);
    }

}
