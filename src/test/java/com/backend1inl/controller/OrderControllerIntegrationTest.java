package com.backend1inl.controller;


import com.backend1inl.TestData;
import com.backend1inl.domain.OrderEntity;
import com.backend1inl.repositories.OrderRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("dev") // har egen properties fil för tester
public class OrderControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderRepository mockOrderRepo;


    @Test
    public void testThatGetAllOrdersReturnsEmptyListIfNoneAnd200() throws Exception {
        String url = "/orders";

        when(mockOrderRepo.findAll()).thenReturn(new ArrayList<>());

        mockMvc.perform(MockMvcRequestBuilders.get(url))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("[]"));
    }

    // Skiter i att kolla om orders har items i detta fall, vi är bara intresserade av x antal orders stämmer och ger status 200
    @Test
    public void testThatGetAllOrdersReturnsOrdersIfExistsAnd200() throws Exception {
        String url = "/orders";
        List<OrderEntity> orderEntities = TestData.listOfOrderEntities();

        String orderListJSON = "[{\"id\":1,\"created\":\"2023-05-01\",\"lastUpdated\":\"2023-05-01\",\"items\":[]},{\"id\":2,\"created\":\"2023-05-01\",\"lastUpdated\":\"2023-05-01\",\"items\":[]},{\"id\":3,\"created\":\"2023-05-01\",\"lastUpdated\":\"2023-05-01\",\"items\":[]}]";

        when(mockOrderRepo.findAll()).thenReturn(orderEntities);

        mockMvc.perform(MockMvcRequestBuilders.get(url))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(orderListJSON));

    }
}
