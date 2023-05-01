package com.backend1inl.controller;

import com.backend1inl.TestData;
import com.backend1inl.repositories.ItemRepository;
import com.backend1inl.services.ItemService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.Arrays;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("dev") // har egen properties fil för tester
public class ItemControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ItemRepository mockRepo;

    @MockBean
    ItemService mockService;

    @Test
    public void testGetAllItemsEndpointLives() throws Exception {

        String url = "/items";

        mockMvc.perform(MockMvcRequestBuilders.get(url))
                .andDo(print())
                .andExpect(status().isOk());

    }

    @Test
    void testGetAllItemsReturnsEmptyList200WhenNoItems() throws Exception {
        String url = "/items";

        when(mockRepo.findAll()).thenReturn(new ArrayList<>());

        mockMvc.perform(MockMvcRequestBuilders.get(url))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("[]"));
    }
//
//    @Test
//    void testGetAllItemsReturnsListOfItems200IfExists() throws Exception {
//        String url = "/items";
//
//        var testDataList = TestData.listOfItemEntities();
//        var targetEntity = testDataList.get(0);
//
//        when(mockRepo.findAll()).thenReturn(testDataList);
//        when(mockService.findAllItems()).thenReturn(Arrays.asList(TestData.testCustomerDTO()));
//
//
//        mockMvc.perform(MockMvcRequestBuilders.get(url))
//                .andDo(print())
//                .andExpect(status().isOk())
//                // testar några st av properties stämmer
//                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(targetEntity.getId()))
//                .andExpect(MockMvcResultMatchers.jsonPath("$[0].firstName").value(targetEntity.getFirstName()))
//                .andExpect(MockMvcResultMatchers.jsonPath("$[0].lastName").value(targetEntity.getLastName()))
//                .andExpect(MockMvcResultMatchers.jsonPath("$[0].ssn").value(targetEntity.getSsn()));
//    }

    @Test
    public void testGetItemId9991DoesNotExist() throws Exception {

        String url = "/items/9991";

        mockMvc.perform(MockMvcRequestBuilders.get(url))
                .andDo(print())
                .andExpect(status().isNotFound());
    }




}
