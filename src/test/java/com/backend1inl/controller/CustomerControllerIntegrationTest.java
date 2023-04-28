package com.backend1inl.controller;

import com.backend1inl.TestData;
import com.backend1inl.exception.NoSuchCustomerException;
import com.backend1inl.repositories.CustomerRepository;
import com.backend1inl.services.CustomerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("dev") // har egen properties fil för tester
public class CustomerControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CustomerRepository mockRepo;

    @MockBean
    CustomerService mockService;

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
        when(mockService.listCustomers()).thenReturn(Arrays.asList(TestData.testCustomerDTO()));


        mockMvc.perform(MockMvcRequestBuilders.get(url))
                .andDo(print())
                .andExpect(status().isOk())
                // testar några st av properties stämmer
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(targetEntity.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].firstName").value(targetEntity.getFirstName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].lastName").value(targetEntity.getLastName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].ssn").value(targetEntity.getSsn()));
    }


    @Test
    public void testThatRetrieveCustomerReturns404WhenNotFound() throws Exception {
        String url = "/customers/99";
        when(mockService.findCustomerById(99L)).thenThrow(NoSuchCustomerException.class);
        mockMvc.perform(MockMvcRequestBuilders.get(url))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void testThatRetrieveCustomerReturnsHttp200WhenCustomerExists() throws Exception {
        var testEntity = TestData.testCustomerEntity();
        var testDto = TestData.testCustomerDTO();

        when(mockRepo.findById(testEntity.getId())).thenReturn(Optional.of(testEntity));
        when(mockService.findCustomerById(testEntity.getId())).thenReturn(testDto);

        String url = "/customers/" + testEntity.getId();

        mockMvc.perform(MockMvcRequestBuilders.get(url))
                .andDo(print())
                .andExpect(status().isOk())
                // kollar av några av props
                .andExpect(MockMvcResultMatchers.jsonPath(
                        "$.id").value(testDto.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath(
                        "$.firstName").value(testDto.getFirstName()))
                .andExpect(MockMvcResultMatchers.jsonPath(
                        "$.lastName").value(testDto.getLastName()));

    }


    @Test
    public void testThatCreateCustomerCreatesNewCustomer201() throws Exception {
        String url = "/customers";

        var testEntity = TestData.testCustomerEntity();
        var testDTO = TestData.testCustomerDTO();

//        when(mockRepo.existsById(testEntity.getId())).thenReturn(true);
//        when(mockService.doesCustomerExist(testDTO)).thenReturn(true);
//        when(mockService.findCustomerById(testDTO.getId())).thenReturn(testDTO);
//        when(mockRepo.findById(testEntity.getId())).thenReturn(Optional.of(testEntity));
        when(mockRepo.save(testEntity)).thenReturn(testEntity);
        when(mockService.create(testDTO)).thenReturn(testDTO);



        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule()); // Dependency for serializing LocalDateTime
        String customerJSON = mapper.writeValueAsString(testDTO);

        mockMvc.perform(MockMvcRequestBuilders.post(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(customerJSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath(
                        "$.id").value(testDTO.getId())
                )
                .andExpect(MockMvcResultMatchers.jsonPath(
                        "$.firstName").value(testDTO.getFirstName())
                )
                .andExpect(MockMvcResultMatchers.jsonPath(
                        "$.lastName").value(testDTO.getLastName())
                )
                .andExpect(MockMvcResultMatchers.jsonPath(
                        "$.created").value(testDTO.getCreated().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                )
                .andExpect(MockMvcResultMatchers.jsonPath(
                        "$.lastUpdated").value(testDTO.getLastUpdated().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                )
                .andExpect(MockMvcResultMatchers.jsonPath(
                        "$.ssn").value(testDTO.getSsn())
                );
    }

    @Test
    public void testThatUpdateExistingCustomerReturns200() throws Exception {
        String url = "/customers";

        var testEntity = TestData.testCustomerEntity();
        var testDTO = TestData.testCustomerDTO();

        // Här mockar vi en DTO/ENTITY och sparar till mockad databas.
        when(mockService.create(testDTO)).thenReturn(testDTO);
        when(mockRepo.save(testEntity)).thenReturn(testEntity);
        var savedDTO = mockService.create(testDTO);


        // Här uppdaterar vi kunden med ett nytt förnamn.
        savedDTO.setFirstName("Testeru");

        // Här mockar vi alla steg som krävs för service.save-metoden
        when(mockRepo.existsById(testEntity.getId())).thenReturn(true);
        when(mockService.doesCustomerExist(testDTO)).thenReturn(true);
        when(mockService.findCustomerById(testDTO.getId())).thenReturn(testDTO);
        when(mockRepo.findById(testEntity.getId())).thenReturn(Optional.of(testEntity));
        when(mockService.save(savedDTO)).thenReturn(savedDTO);


        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule()); // Dependency for serializing LocalDate
        String customerJSON = mapper.writeValueAsString(testDTO);

        mockMvc.perform(MockMvcRequestBuilders.post(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(customerJSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath(
                        "$.id").value(savedDTO.getId())
                )
                .andExpect(MockMvcResultMatchers.jsonPath(
                        "$.firstName").value(savedDTO.getFirstName())
                )
                .andExpect(MockMvcResultMatchers.jsonPath(
                        "$.lastName").value(savedDTO.getLastName())
                )
                .andExpect(MockMvcResultMatchers.jsonPath(
                        "$.created").value(savedDTO.getCreated().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                )
                .andExpect(MockMvcResultMatchers.jsonPath(
                        "$.lastUpdated").value(savedDTO.getLastUpdated().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                )
                .andExpect(MockMvcResultMatchers.jsonPath(
                        "$.ssn").value(savedDTO.getSsn())
                );
    }

    @Test
    public void testDeleteCustomerThatDoesntExistReturns200WithDeleteResponseFalse() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/customers/497984739827"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.deleted").value(false));
    }

    @Test
    public void testDeleteCustomerThatExistsReturns200WithDeleteResponseTrue() throws Exception {

        String url = "/customers/";

        var testEntity = TestData.testCustomerEntity();
        var testDTO = TestData.testCustomerDTO();

        when(mockRepo.save(testEntity)).thenReturn(testEntity);
        when(mockService.save(testDTO)).thenReturn(testDTO);

        var target = mockService.save(testDTO);

        when(mockService.deleteCustomerById(target.getId())).thenReturn(true);

        mockMvc.perform(MockMvcRequestBuilders.delete(url + target.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath(
                        "$.deleted").value(true));
    }

}
