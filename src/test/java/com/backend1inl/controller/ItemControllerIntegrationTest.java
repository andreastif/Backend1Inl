package com.backend1inl.controller;

import com.backend1inl.TestData;
import com.backend1inl.repositories.ItemRepository;
import com.backend1inl.services.ItemService;

import com.backend1inl.utils.DeleteResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;


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
    private ItemService mockService;


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

    @Test
    public void testThatFileCanBeUploadedToDb() throws Exception {
        //https://www.baeldung.com/sprint-boot-multipart-requests

        //urlen vi skall POSTA till
        String url = "/items";

        //När vi ska skapa ett nytt item och ladda upp ett objekt behöver vi säkerställa att båda RequestParts blir skickade och skapade!
        //Således:
        //För att testa båda RequestParts måste vi först skapa en dummy-fil och ett stringifierat Json Objekt (en string som kan formatteras till JSON)!
        //Anledningen är att vi använder en ObjectMapper när vi skapar ett Item Objekt.

        //Exempel på dummy-fil
        //name i MockMultiPartFile == "file".  Måste motsvara namnet på MultiPartFile som skickas in i metoden i controllern
        MockMultipartFile testFile = new MockMultipartFile("file",
                "foo.txt",
                MediaType.TEXT_PLAIN_VALUE,
                "Hello world".getBytes());

        //Exempel på Stringifierat JsonObjekt:
        // A) För att få reda på ContentType, se ItemController och vad MediaType.xx returnerar på metoden
        // ELLER
        // B) Skriva MediaType.xx direkt i MockMultiPartFile!
        // Name i MockMultiPartFile == "itemString".  Måste motsvara namnet på String som skickas in i metoden i controllern
        MockMultipartFile itemString =
                new MockMultipartFile(
                        "itemString",
                        null,
                        MediaType.APPLICATION_JSON_VALUE,
                        "{\"name\": \"myName\",\"price\":1,\"balance\":1}".getBytes());

        //Själva testet som körs. Notera att det är .multipart(url) som används, ej post/get/put/delete osv.
        mockMvc.perform(MockMvcRequestBuilders
                .multipart(url)
                .file(testFile)
                .file(itemString))
                .andDo(print())
                .andExpect(status().isCreated());

    }

    @Test
    public void testThatUpdatePriceReturns200() throws Exception {

        var mpf = TestData.multiPartFile();
        var testEntity = TestData.testItemEntity();
        var testDTO = TestData.testItem();

        //Instruktioner till den mockade servicen och repository:t  -> när x ... gör ... y
        when(mockService.createNewItemEntity(testDTO, mpf)).thenReturn(testDTO);
        when(mockRepo.save(testEntity)).thenReturn(testEntity);
        //Användandet av det mockade repository:t och servicen
        var savedDTO = mockService.createNewItemEntity(testDTO, mpf);

        savedDTO.setPrice(44L);

        when(mockRepo.existsById(testEntity.getId())).thenReturn(true);
        when(mockService.findItemEntityById(testDTO.getId())).thenReturn(Optional.of(testDTO));
        when(mockRepo.findById(testEntity.getId())).thenReturn(Optional.of(testEntity));
        when(mockService.updatePriceOfItemEntity(savedDTO.getPrice(),savedDTO.getId())).thenReturn(savedDTO);

        mockMvc.perform(MockMvcRequestBuilders.put("/items/" + savedDTO.getId() + "/price")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(String.valueOf(44L)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.price").value(44L));

    }

    @Test
    public void testThatUpdateNameReturns200() throws Exception {

        var mpf = TestData.multiPartFile();
        var testEntity = TestData.testItemEntity();
        var testDTO = TestData.testItem();

        //Instruktioner till den mockade servicen och repository:t  -> när x ... gör ... y
        when(mockService.createNewItemEntity(testDTO, mpf)).thenReturn(testDTO);
        when(mockRepo.save(testEntity)).thenReturn(testEntity);
        //Användandet av det mockade repository:t och servicen
        var savedDTO = mockService.createNewItemEntity(testDTO, mpf);

        savedDTO.setName("CENA");

        when(mockRepo.existsById(testEntity.getId())).thenReturn(true);
        when(mockService.findItemEntityById(testDTO.getId())).thenReturn(Optional.of(testDTO));
        when(mockRepo.findById(testEntity.getId())).thenReturn(Optional.of(testEntity));
        when(mockService.updateNameOfItemEntity(savedDTO.getName(),savedDTO.getId())).thenReturn(savedDTO);

        mockMvc.perform(MockMvcRequestBuilders.put("/items/" + savedDTO.getId() + "/name")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content("CENA"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("CENA"));
    }


    @Test
    void testThatUpdateBalanceReturns200() throws Exception {

        var mpf = TestData.multiPartFile();
        var testEntity = TestData.testItemEntity();
        var testDTO = TestData.testItem();

        when(mockService.createNewItemEntity(testDTO, mpf)).thenReturn(testDTO);
        when(mockRepo.save(testEntity)).thenReturn(testEntity);

        var savedDTO = mockService.createNewItemEntity(testDTO, mpf);

        savedDTO.setBalance(22L);

        when(mockRepo.existsById(testEntity.getId())).thenReturn(true);
        when(mockService.findItemEntityById(testDTO.getId())).thenReturn(Optional.of(testDTO));
        when(mockRepo.findById(testEntity.getId())).thenReturn(Optional.of(testEntity));
        when(mockService.updateBalanceOfItemEntity(savedDTO.getBalance(),savedDTO.getId())).thenReturn(savedDTO);

        mockMvc.perform(MockMvcRequestBuilders.put("/items/" + savedDTO.getId() + "/balance")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(String.valueOf(22L)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.balance").value(22L));

    }

    @Test
    public void testThatDeleteReturns204NoContent() throws Exception {

        var mpf = TestData.multiPartFile();
        var testDTO = TestData.testItem();
        var testEntity = TestData.testItemEntity();

        //Instruktioner till den mockade servicen och repository:t  -> när x ... gör ... y
        when(mockService.createNewItemEntity(testDTO, mpf)).thenReturn(testDTO);
        when(mockRepo.save(testEntity)).thenReturn(testEntity);

        //Användandet av det mockade repository:t och servicen
        var savedDTO = mockService.createNewItemEntity(testDTO, mpf);

        when(mockService.deleteItemEntityById(savedDTO.getId())).thenReturn(new DeleteResponse(true));

        mockMvc.perform(MockMvcRequestBuilders.delete("/items/" + savedDTO.getId() + "/delete" ))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.deleted").value(true));

    }

    @Test
    public void testThatDeleteThrowsNoSuchItemException404() throws Exception {

        when(mockService.deleteItemEntityById(8187181L)).thenReturn(new DeleteResponse(false));

        mockMvc.perform(MockMvcRequestBuilders.delete("/items/" + 8187181 + "/delete" ))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.deleted").value(false));

    }


    @Test
    public void testGetItemId9991DoesNotExist() throws Exception {

        String url = "/items/9991";

        mockMvc.perform(MockMvcRequestBuilders.get(url))
                .andDo(print())
                .andExpect(status().isNotFound());
    }




}
