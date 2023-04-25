package com.backend1inl.controller;

import com.backend1inl.TestData;
import com.backend1inl.repositories.CustomerRepository;
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

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("testmock") // har egen properties fil för tester
public class CustomerControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CustomerRepository mockRepo;

    @Test
    void testGetAllCustomersEndpointLives() throws Exception {
        String url = "/customers";
        mockMvc.perform(MockMvcRequestBuilders.get(url))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void testGetAllCustomersReturnsEmptyList200WhenNoCustomers() throws Exception {
        String url = "/customers";

        when(mockRepo.findAll()).thenReturn(new ArrayList<>());

        mockMvc.perform(MockMvcRequestBuilders.get(url))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("[]"));
    }

    @Test
    void testGetAllCustomersReturnsListOfCustomers200IfExists() throws Exception {
        String url = "/customers";

        var testDataList = TestData.listOfCustomerEntities();
        var targetEntity = testDataList.get(0);

        when(mockRepo.findAll()).thenReturn(testDataList);

        mockMvc.perform(MockMvcRequestBuilders.get(url))
                .andDo(print())
                .andExpect(status().isOk())
                // testar några st av properties stämmer
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(targetEntity.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].firstName").value(targetEntity.getFirstName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].lastName").value(targetEntity.getLastName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].ssn").value(targetEntity.getSsn()));
    }

    // TODO.. retrieveCustomer(), delete() update()/Create customer beroende på va sigge säger
}
