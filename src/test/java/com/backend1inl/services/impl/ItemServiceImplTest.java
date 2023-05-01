package com.backend1inl.services.impl;

import com.backend1inl.TestData;
import com.backend1inl.repositories.ItemRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import com.backend1inl.TestData;
import com.backend1inl.repositories.CustomerRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;


import static org.assertj.core.api.Assertions.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import static org.assertj.core.api.Assertions.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import java.io.File;
import java.io.FileInputStream;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("dev") // har egen properties fil för tester
@ExtendWith(MockitoExtension.class)
public class ItemServiceImplTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock //== Autowired (fast i testmiljö)
    private ItemRepository itemRepository;

    @InjectMocks //== Autowired (i testmiljö, för service)
    ItemServiceImpl mockServiceImpl;

    @Test
    public void testThatFileCanBeUploadedToDb() throws Exception {
        //Nice to have - skip for now (clues -->)
        // 1. https://www.tabnine.com/code/java/classes/org.springframework.mock.web.MockMultipartFile
        // 2. https://stackoverflow.com/questions/20389746/how-to-post-multipart-form-data-for-a-file-upload-using-springmvc-and-mockmvc/20390802#20390802
        // 3. https://www.logicbig.com/examples/spring-mvc/file-upload/spring-mock-mvc-request-builders-multipart.html
    }

    @Test
    public void testThatItemIsSaved() throws IOException {
        var testDTO = TestData.testItem();
        var testEntity = TestData.testItemEntity();

        MultipartFile mpf = TestData.multiPartFile();

        when(itemRepository.save(testEntity)).thenReturn(testEntity);

        var savedDTO = mockServiceImpl.createNewItemEntity(testDTO, mpf);
        //funderar om vi måste sätta imgData i buildern på testDAta? aight
        assertThat(savedDTO).isEqualTo(testDTO);
    }

}
