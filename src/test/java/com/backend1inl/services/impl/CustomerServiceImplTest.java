package com.backend1inl.services.impl;

import com.backend1inl.domain.Customer;
import com.backend1inl.domain.CustomerEntity;
import com.backend1inl.repositories.CustomerRepository;
import net.bytebuddy.asm.Advice;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class CustomerServiceImplTest {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerServiceImpl underTest;

    @Test
    public void testThatCustomerIsSaved() {
        final Customer customer = Customer.builder()
                .id(1L)
                .firstName("Test")
                .lastName("Testsson")
                .ssn("9112200812")
/*                .created(LocalDateTime.of(1991,12,23,12,12,12))
                .lastUpdated(LocalDateTime.of(1991,12,23,12,12,12))*/
                .created(LocalDateTime.now())
                .lastUpdated(LocalDateTime.now())
                .build();

        final CustomerEntity customerEntity = CustomerEntity.builder()
                .id(1L)
                .firstName("Test")
                .lastName("Testsson")
                .ssn("9112200812")
                .created(LocalDateTime.now())
                .lastUpdated(LocalDateTime.now())
                .build();

                when(customerRepository.save(customerEntity)).thenReturn(customerEntity);
//        when(customerRepository.save(Mockito.eq(customerEntity))).thenReturn(customerEntity);

        final Customer result = underTest.create(customer);
        assertEquals(customer, result);

    }
}
