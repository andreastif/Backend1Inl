package com.backend1inl.services;

import com.backend1inl.domain.Item;
import com.backend1inl.domain.ItemEntity;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;


public interface ItemService {

    Item saveItem(Item item, MultipartFile file) throws IOException;

    Item stringItemToJson(String itemString) throws JsonProcessingException;

    List<Item> findAllItems();

    Optional<byte[]> findItemImageById(Long id);

    Optional<Item> findItemById(Long id);

    ItemEntity itemToItemEntity(Item item, MultipartFile file) throws IOException;

    Item itemEntityToItem(ItemEntity itemEntity);

    String itemURIBuilder(Long id);

    void deleteImageEntityById(Long id);



}
