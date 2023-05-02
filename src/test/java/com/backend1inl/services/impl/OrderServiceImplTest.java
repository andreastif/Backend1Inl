package com.backend1inl.services.impl;


import com.backend1inl.TestData;
import com.backend1inl.exception.NoSuchOrderException;
import com.backend1inl.repositories.OrderRepository;
import com.backend1inl.utils.DeleteResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.*;

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
    public void testThatDeleteByIdReturnsFalseIfNotExists() {
        var result = underTest.deleteOrderById(6L);

        assertThat(result)
                .isEqualTo(new DeleteResponse(false));
    }

    @Test
    public void testThatDeleteByIdReturnsTrueIfExists() {
        // Mockad entity som skall finnas på ...orders/1
        var orderEntity = TestData.orderEntity();
        when(orderRepository.findById(orderEntity.getId())).thenReturn(Optional.of(orderEntity));

        // Vi använder riktiga service och gör en delete
        var result = underTest.deleteOrderById(orderEntity.getId());

        // verifierar att service.deleteById(1) har kallats 1 gång.
        verify(orderRepository, times(1)).deleteById(orderEntity.getId());

        assertThat(result)
                .isEqualTo(new DeleteResponse(true));
    }

}
