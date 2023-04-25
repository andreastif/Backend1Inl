package com.backend1inl.services.impl;

import com.backend1inl.domain.Customer;
import com.backend1inl.domain.CustomerEntity;
import com.backend1inl.exception.NoSuchCustomerException;
import com.backend1inl.repositories.CustomerRepository;
import com.backend1inl.services.CustomerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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
    public Customer findCustomerById(Long id) {

        return customerRepo.findById(id)
                .map(customerEntity -> customerEntityToCustomer(customerEntity))
                .orElseThrow(() -> new NoSuchCustomerException("Customer with id: " + id + " doesn't exist"));
    }



    //UPDATE
    @Override
    public Customer save(Customer customer) {
        boolean exist = doesCustomerExist(customer);

        if(exist) {
            Customer match = findCustomerById(customer.getId());

            match.setLastUpdated(LocalDateTime.now());
            match.setSsn(customer.getSsn());
            match.setLastName(customer.getLastName());
            match.setFirstName(customer.getFirstName());
            match.setCreated(match.getCreated());

            final CustomerEntity savedCustomerEntity = customerRepo.save(customerToCustomerEntity(match));
            return customerEntityToCustomer(savedCustomerEntity);
        }

        var entityToSave = customerToCustomerEntity(customer);
        var savedCustomerEntity = customerRepo.save(entityToSave);

        return customerEntityToCustomer(savedCustomerEntity);
    }



    //DELETE
    @Override
    public boolean deleteCustomerById(Long id) {
        Optional<CustomerEntity> match = customerRepo.findById(id);
        if(match.isPresent()) {
            customerRepo.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public boolean doesCustomerExist(Customer customer) {
       return customerRepo.existsById(customer.getId());
    }


    //CONVERTER
    private CustomerEntity customerToCustomerEntity(Customer customer){
        return CustomerEntity.builder()
                .id(customer.getId())
                .firstName(customer.getFirstName())
                .lastName(customer.getLastName())
                .ssn(customer.getSsn())
                .created(customer.getCreated())
                .lastUpdated(LocalDateTime.now())
                .build();
    }

    private Customer customerEntityToCustomer(CustomerEntity customerEntity){
        return Customer.builder()
                .id(customerEntity.getId())
                .firstName(customerEntity.getFirstName())
                .lastName(customerEntity.getLastName())
                .ssn(customerEntity.getSsn())
                .created(customerEntity.getCreated())
                .lastUpdated(customerEntity.getLastUpdated())
                .build();
    }

}
