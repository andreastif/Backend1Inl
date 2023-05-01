package com.backend1inl.controllers;

import com.backend1inl.domain.Item;
import com.backend1inl.exception.NoSuchItemException;
import com.backend1inl.services.ItemService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;


@RestController
@RequestMapping("/items")
@Slf4j
public class ItemController {

    //TODO: SAKNAR UNIT + INTEGRATIONSTEST

    private final ItemService itemService;

    @Autowired
    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    //get all items
    @GetMapping
    public ResponseEntity<?> getItems() {
        return new ResponseEntity<>(itemService.findAllItems(), HttpStatus.OK);
    }

    //get one item
    @GetMapping("/{id}")
    public ResponseEntity<Item> getItem(@PathVariable Long id) {
        log.info("GET: Item with id {}", id);
        return itemService.findItemEntityById(id).map(item -> new ResponseEntity<>(item, HttpStatus.OK)).orElseThrow(() -> new NoSuchItemException("Item with id: " + id + " doesn't exist"));
    }

    //TODO NICE TO HAVE: VALIDERA ATT DET ÄR EN JPEG/PNG SOM SKICKAS
    //get item image
    @GetMapping("/{id}/img")
    public ResponseEntity<byte[]> getItemImage(@PathVariable Long id) {
        log.info("GET: Item image with id {}", id);
        return itemService.findItemImageById(id).map(bytes -> ResponseEntity.status(HttpStatus.OK).contentType(MediaType.valueOf("image/png")).body(bytes)).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    //delete item by id
    @DeleteMapping("/{id}/delete")
    public ResponseEntity<?> deleteItemById(@PathVariable Long id) {
        log.info("DELETE: Item id {}", id);
        var response = itemService.deleteItemEntityById(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    //create new items
    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<Item> createNewItemEntity(@Valid @RequestPart String itemString, @RequestPart MultipartFile file) throws IOException {
        log.info("POST (CREATE): Item {} and MultipartFile {}", itemString, file.getOriginalFilename());
        Item savedItem = itemService.createNewItemEntity(itemService.stringItemToJson(itemString), file);
        return new ResponseEntity<>(savedItem, HttpStatus.CREATED);
    }

    //update existing item
    @PutMapping(value = "/{id}", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<?> updateItemEntityById(@Valid @RequestPart String itemString, @RequestPart MultipartFile file, @PathVariable Long id) throws IOException {
        log.info("PUT (UPDATE): Item id {}", id);
        Item savedItem = itemService.updateItemEntity(itemService.stringItemToJson(itemString), file, id);
        return new ResponseEntity<>(savedItem, HttpStatus.OK);

    }

    //update price
    @PutMapping(value = "/{id}/price")
    public ResponseEntity<?> updateItemEntityPriceById(@RequestBody Long price, @PathVariable Long id) {
        log.info("PUT (UPDATE): PRICE of item id {}", id);
        Item updatedItem = itemService.updatePriceOfItemEntity(price, id);
        return new ResponseEntity<>(updatedItem, HttpStatus.OK);

    }

    //update name
    @PutMapping(value = "/{id}/name")
    public ResponseEntity<?> updateItemEntityNameById(@RequestBody String name, @PathVariable Long id) {
        log.info("PUT (UPDATE): NAME of item id {}", id);
        Item updatedItem = itemService.updateNameOfItemEntity(name, id);
        return new ResponseEntity<>(updatedItem, HttpStatus.OK);

    }

    //update balance
    @PutMapping(value = "/{id}/balance")
    public ResponseEntity<?> updateItemEntityBalanceById(@RequestBody Long balance, @PathVariable Long id) {
            log.info("PUT (UPDATE): balance of item id {}", id);
            Item updatedItem = itemService.updateBalanceOfItemEntity(balance, id);
            return new ResponseEntity<>(updatedItem, HttpStatus.OK);

    }

}
