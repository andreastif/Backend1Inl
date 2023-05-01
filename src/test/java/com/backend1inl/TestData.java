package com.backend1inl;

import com.backend1inl.domain.Customer;
import com.backend1inl.domain.CustomerEntity;
import com.backend1inl.domain.Item;
import com.backend1inl.domain.ItemEntity;
import com.backend1inl.utils.ItemImageUtils;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TestData {

    public static CustomerEntity testCustomerEntity() {
        return CustomerEntity.builder()
                .id(1L)
                .created(LocalDate.now())
                .lastUpdated(LocalDate.now())
                .firstName("Test")
                .lastName("Test")
                .ssn("9108233876")
                .build();
    }

    public static Customer testCustomerDTO() {
        return Customer.builder()
                .id(1L)
                .created(LocalDate.now())
                .lastUpdated(LocalDate.now())
                .firstName("Test")
                .lastName("Test")
                .ssn("9108233876")
                .build();
    }

    public static List<CustomerEntity> listOfCustomerEntities() {
        return Arrays.asList(
                testCustomerEntity()
        );
    }


    //
    public static MultipartFile multiPartFile() {
        //should work, om denna funkar så kan vi ha det som "testfil" substitute. vi validerar ändå inte .jpeg/png osv (nice to have, hittade en guide för det)
        return new MockMultipartFile("foo",
                "foo.txt",
                MediaType.TEXT_PLAIN_VALUE,
                "Hello world".getBytes());
    }

    public static ItemEntity testItemEntity() throws IOException {
        return ItemEntity.builder()
                .id(1L)
                .created(LocalDate.now())
                .lastUpdated(LocalDate.now())
                .imgData(ItemImageUtils.compressImage(multiPartFile().getBytes()))
                .name("Bullens pilsnerkorv")
                .balance(15L)
                .price(33L)
                .build();
    }

    public static Item testItem() throws IOException {
        return Item.builder()
                .id(1L)
                .created(LocalDate.now())
                .lastUpdated(LocalDate.now())
                .name("Bullens pilsnerkorv")
                .balance(15L)
                .price(33L)
                .URI("test")
                .build();
    }

    public static List<ItemEntity> listOfItemEntities() throws IOException {
        return Arrays.asList(
                testItemEntity()
        );
    }
}
