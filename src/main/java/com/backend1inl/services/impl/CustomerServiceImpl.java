package com.backend1inl.services.impl;

import com.backend1inl.domain.Customer;
import com.backend1inl.domain.CustomerEntity;
import com.backend1inl.repositories.CustomerRepository;
import com.backend1inl.services.CustomerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepo;

    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepo) {
        this.customerRepo = customerRepo;
    }


    //CREATE
    @Override
    public Customer create(Customer customer) {
        final CustomerEntity customerEntity = customerToCustomerEntity(customer);
        final CustomerEntity savedCustomerEntity = customerRepo.save(customerEntity);

        return customerEntityToCustomer(savedCustomerEntity);
    }

    //READ
    @Override
    public List<Customer> listCustomers() {
        final List<CustomerEntity> foundCustomers = customerRepo.findAll();
        return foundCustomers.stream().map(customer -> customerEntityToCustomer(customer)).collect(Collectors.toList());
    }

    @Override
    public Optional<Customer> findCustomerById(Long id) {
        final Optional<CustomerEntity> foundCustomer = customerRepo.findById(id);
        return foundCustomer.map(customer -> customerEntityToCustomer(customer));

    }

    //UPDATE

    //DELETE


    //CONVERTER
    private CustomerEntity customerToCustomerEntity(Customer customer){
        return CustomerEntity.builder()
                .id(customer.getId())
                .firstName(customer.getFirstName())
                .lastName(customer.getLastName())
                .ssn(customer.getSsn())
                .build();
    }

    private Customer customerEntityToCustomer(CustomerEntity customerEntity){
        return Customer.builder()
                .id(customerEntity.getId())
                .firstName(customerEntity.getFirstName())
                .lastName(customerEntity.getLastName())
                .ssn(customerEntity.getSsn())
                .build();
    }

}
