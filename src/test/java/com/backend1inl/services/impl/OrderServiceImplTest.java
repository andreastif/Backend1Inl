package com.backend1inl.services.impl;


import com.backend1inl.TestData;
import com.backend1inl.repositories.OrderRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import static org.assertj.core.api.Assertions.*;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class OrderServiceImplTest {

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private OrderServiceImpl underTest;


    @Test
    public void testGetAllOrdersReturnsCorrectIfOrdersExists() throws IOException {

        when(orderRepository.findAll()).thenReturn(TestData.listOfOrderEntities());

        var result = underTest.getAllOrders();

        assertThat(result)
                .isNotEmpty()
                .hasSize(3);
    }

    /*
    @Test
    public void testThatGetOrderByIdReturnsCorretOrder

     */
}
