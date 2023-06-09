package com.backend1inl.services;

import com.backend1inl.domain.Customer;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


public interface CustomerService {

    List<Customer> listCustomers();

    Customer findCustomerById(Long id);

    Customer create(Customer customer);

    Customer save(Customer customer);
    boolean deleteCustomerById(Long id);

    boolean doesCustomerExist(Customer customer);
}
