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
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

        String currentDate = orderEntities.get(0)
                .getCreated().
                format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        String lastUpdated = orderEntities.get(0)
                .getLastUpdated().
                format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));


        String orderListJSON = "[{\"id\":1,\"created\":\""+currentDate+"\" ,\"lastUpdated\":\""+lastUpdated+"\",\"items\":[]},{\"id\":2,\"created\":\""+currentDate+"\",\"lastUpdated\":\""+lastUpdated+"\",\"items\":[]},{\"id\":3,\"created\":\""+currentDate+"\",\"lastUpdated\":\""+lastUpdated+"\",\"items\":[]}]";

        when(mockOrderRepo.findAll()).thenReturn(orderEntities);

        mockMvc.perform(MockMvcRequestBuilders.get(url))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(orderListJSON));

    }

    @Test
    public void testThatFindOrderByIdReturnsCorrectOrderAnd200() throws Exception {
        var testDTO = TestData.orderDTO();
        var testEntity = TestData.orderEntity();
        String url = "/orders/" + testDTO.getId();

        when(mockOrderRepo.findById(testDTO.getId())).thenReturn(Optional.of(testEntity));


        mockMvc.perform(MockMvcRequestBuilders.get(url))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath(
                        "$.id").value(testDTO.getId())
                )
                .andExpect(MockMvcResultMatchers.jsonPath(
                        "$.lastUpdated").value(testDTO.getLastUpdated().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                )
                .andExpect(MockMvcResultMatchers.jsonPath(
                        "$.created").value(testDTO.getLastUpdated().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                );
    }
}
