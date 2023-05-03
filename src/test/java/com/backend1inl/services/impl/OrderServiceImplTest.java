package com.backend1inl.services.impl;


import com.backend1inl.TestData;
import com.backend1inl.domain.OrderDTO;
import com.backend1inl.domain.OrderEntity;
import com.backend1inl.exception.NoSuchOrderException;
import com.backend1inl.repositories.CustomerRepository;
import com.backend1inl.repositories.OrderRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OrderServiceImplTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private OrderServiceImpl underTest;


    @Test
    public void testGetAllOrdersReturnsCorrectIfOrdersExists() throws IOException {

        when(orderRepository.findAll()).thenReturn((List<OrderEntity>) TestData.listOfOrderEntities());

        var result = underTest.getAllOrders();

        assertThat(result)
                .isNotEmpty()
                .hasSize(3);
    }


    @Test
    public void testThatGetOrderByIdReturnsCorrectOrderIfExist() {
        var orderEntity = TestData.orderEntity();
        var orderDTO = TestData.orderDTO();

        when(orderRepository.findById(orderEntity.getId())).thenReturn(Optional.of(orderEntity));

        var result = underTest.getItemsByOrderId(orderDTO.getId());

        assertThat(result)
                .isEqualTo(orderDTO);
    }

    @Test
    public void testThatFindOrderByIdThrowsNoSuchOrderExceptionIfNotExists() {
        final Long targetId = 1L;
        assertThatThrownBy(() -> underTest.getItemsByOrderId(targetId)).isInstanceOf(NoSuchOrderException.class);

    }

    @Test
    public void testThatFindOrdersByCustomerIdReturnsEmptyListIfNoOrders() {
        var customerEntity = TestData.testCustomerEntity();

       when(customerRepository.findById(customerEntity.getId())).thenReturn(Optional.of(customerEntity));

       List< OrderDTO> listOfOrdersOnCustomer = underTest.getOrdersByCustomerId(customerEntity.getId());
        assertThat(listOfOrdersOnCustomer)
                .isEmpty();
    }

    @Test
    public void testThatFindOrdersByCustomerIdReturnsCorrectListIfExist() {
        var customerEntity = TestData.testCustomerEntityWithOrders();
        when(customerRepository.findById(customerEntity.getId())).thenReturn(Optional.of(customerEntity));

        var result = underTest.getOrdersByCustomerId(customerEntity.getId());

        // https://assertj.github.io/doc/
        assertThat(result)
                .hasSize(3)
                .extracting(OrderDTO::getId)
                .contains(1L, 2L, 3L);
    }


}
