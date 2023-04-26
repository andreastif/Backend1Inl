package com.backend1inl.services.impl;


import com.backend1inl.TestData;
import com.backend1inl.repositories.CustomerRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.mockito.junit.jupiter.MockitoExtension;



import java.util.Arrays;


import static org.assertj.core.api.Assertions.*;

import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class CustomerServiceImplTest {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerServiceImpl underTest;



    @Test
    public void testThatCustomerIsSaved() {
        var testDTO = TestData.testCustomerDTO();
        var testEntity = TestData.testCustomerEntity();

        when(customerRepository.save(testEntity)).thenReturn(testEntity);

        var savedDTO = underTest.create(testDTO);

        assertThat(savedDTO)
                .isEqualTo(testDTO);
    }


    @Test
    public void testGetAllCustomersReturnsCorrectSize() {
        var testEntity = TestData.testCustomerEntity();

        when(customerRepository.findAll()).thenReturn(Arrays.asList(testEntity));

        var allDtos = underTest.listCustomers();

        assertThat(allDtos)
                .isNotEmpty()
                .hasSize(1);
    }



}

