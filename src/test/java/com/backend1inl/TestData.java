package com.backend1inl;

import com.backend1inl.domain.Customer;
import com.backend1inl.domain.CustomerEntity;

import java.time.LocalDateTime;

public class TestData {

    public static CustomerEntity testCustomerEntity() {
        return CustomerEntity.builder()
                .id(1L)
                .created(LocalDateTime.now())
                .lastUpdated(LocalDateTime.now())
                .firstName("Test")
                .lastName("Test")
                .ssn("9108233876")
                .build();
    }

    public static Customer testCustomerDTO() {
        return Customer.builder()
                .id(1L)
                .created(LocalDateTime.now())
                .lastUpdated(LocalDateTime.now())
                .firstName("Test")
                .lastName("Test")
                .ssn("9108233876")
                .build();
    }
}
