package com.backend1inl.controllers;

import com.backend1inl.domain.Item;
import com.backend1inl.services.ItemService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;

@RestController
public class ItemController {

    private final ItemService itemService;

    @Autowired
    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    //get all items
    @GetMapping("/items")
    public ResponseEntity<?> getItems() {
        return new ResponseEntity<>(itemService.findAllItems(), HttpStatus.OK);
    }

    //get one item
    @GetMapping("/items/{id}")
    public ResponseEntity<Item> getItem(@PathVariable Long id) {
        return itemService.findItemById(id).map(item -> new ResponseEntity<>(item, HttpStatus.OK)).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    //get item image
    @GetMapping("/items/{id}/img")
    public ResponseEntity<byte[]> getItemImage(@PathVariable Long id) {
        return itemService.findItemImageById(id).map(bytes -> ResponseEntity.status(HttpStatus.OK).contentType(MediaType.valueOf("image/png")).body(bytes))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    //delete item by id
    @DeleteMapping("/items/{id}/delete")
    public ResponseEntity<?> deleteItemById(@PathVariable Long id) {
        itemService.deleteImageEntityById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    //create new items
    @PutMapping(value = "/items/add", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<Item> addItem(@RequestPart String itemString, @RequestPart MultipartFile file) throws IOException {
        Item savedItem = itemService.saveItem(itemService.stringItemToJson(itemString), file);
        return new ResponseEntity<>(savedItem, HttpStatus.CREATED);

    }


}
