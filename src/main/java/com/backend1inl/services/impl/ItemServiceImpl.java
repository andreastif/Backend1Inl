package com.backend1inl.services.impl;

import com.backend1inl.domain.Item;
import com.backend1inl.domain.ItemEntity;
import com.backend1inl.repositories.ItemRepository;
import com.backend1inl.services.ItemService;
import com.backend1inl.utils.ItemImageUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;

    @Autowired
    public ItemServiceImpl(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    @Override
    public Item saveItem(Item item, MultipartFile file) throws IOException {
        ItemEntity itemEntityToBeSaved = itemToItemEntity(item, file);
        ItemEntity savedItemEntity = itemRepository.save(itemEntityToBeSaved);
        return itemEntityToItem(savedItemEntity);
    }

    @Override
    public Item stringItemToJson(String itemString) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(itemString, Item.class);
    }

    @Override
    public List<Item> findAllItems() {
        return itemRepository.findAll().stream().map(this::itemEntityToItem).toList();
    }

    @Override
    public Optional<byte[]> findItemImageById(Long id) {
        var fetchedItemEntity = itemRepository.findById(id);
        return fetchedItemEntity.map(itemEntity -> ItemImageUtils.decompressImage(itemEntity.getImgData()));
    }

    @Override
    public Optional<Item> findItemById(Long id) {
        var fetchedItemEntity = itemRepository.findById(id);
        return fetchedItemEntity.map(this::itemEntityToItem);
    }

    @Override
    public ItemEntity itemToItemEntity(Item item, MultipartFile file) throws IOException {
            return ItemEntity
                    .builder()
                    .name(item.getName())
                    .price(item.getPrice())
                    .saldo(item.getSaldo())
                    .imgData(ItemImageUtils.compressImage(file.getBytes()))
                    .created(LocalDateTime.now())
                    .lastUpdated(LocalDateTime.now())
                    .build();
    }

    @Override
    public Item itemEntityToItem(ItemEntity itemEntity) {
        return Item
                .builder()
                .id(itemEntity.getId())
                .price(itemEntity.getPrice())
                .saldo(itemEntity.getSaldo())
                .created(itemEntity.getCreated())
                .lastUpdated(itemEntity.getLastUpdated())
                .URI(itemURIBuilder(itemEntity.getId()))
                .name(itemEntity.getName())
                .build();
    }


    @Override
    public void deleteImageEntityById(Long id) {
        try {
            itemRepository.deleteById(id);
        } catch (EmptyResultDataAccessException ex) {
            log.debug("Attempted to delete non-existing item", ex);
        }
    }

    @Override
    public String itemURIBuilder(Long id) {
        return ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/items/")
                .path(String.valueOf(id))
                .path("/img")
                .toUriString();
    }
}
