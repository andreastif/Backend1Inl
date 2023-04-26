package com.backend1inl;

import com.backend1inl.domain.Customer;
import com.backend1inl.domain.CustomerEntity;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

public class TestData {

    public static CustomerEntity testCustomerEntity() {
        return CustomerEntity.builder()
                .id(1L)
                .created(LocalDate.now())
                .lastUpdated(LocalDate.now())
                .firstName("Test")
                .lastName("Test")
                .ssn("9108233876")
                .build();
    }

    public static Customer testCustomerDTO() {
        return Customer.builder()
                .id(1L)
                .created(LocalDate.now())
                .lastUpdated(LocalDate.now())
                .firstName("Test")
                .lastName("Test")
                .ssn("9108233876")
                .build();
    }

    public static List<CustomerEntity> listOfCustomerEntities() {
        return Arrays.asList(
                testCustomerEntity()
        );
    }
}
