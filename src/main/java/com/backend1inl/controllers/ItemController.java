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
import java.util.Optional;

@RestController
@RequestMapping("/items")
public class ItemController {

    //TODO: SAKNAR VERIFIKATION (VALID)
    //TODO: SAKNAR INTEGRATIONSTEST
    //TODO: SAKNAR HATEOAS


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
        return itemService.findItemEntityById(id).map(item -> new ResponseEntity<>(item, HttpStatus.OK)).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    //get item image
    @GetMapping("/{id}/img")
    public ResponseEntity<byte[]> getItemImage(@PathVariable Long id) {
        return itemService.findItemImageById(id).map(bytes -> ResponseEntity.status(HttpStatus.OK).contentType(MediaType.valueOf("image/png")).body(bytes))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    //delete item by id
    @DeleteMapping("/{id}/delete")
    public ResponseEntity<?> deleteItemById(@PathVariable Long id) {
        itemService.deleteItemEntityById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    //create new items
    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<Item> createNewItemEntity(@RequestPart String itemString, @RequestPart MultipartFile file) throws IOException {
        Item savedItem = itemService.createNewItemEntity(itemService.stringItemToJson(itemString), file);
        return new ResponseEntity<>(savedItem, HttpStatus.CREATED);
    }

    //update existing item
    @PutMapping(value="/{id}", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<?> updateItemEntityById(@RequestPart String itemString, @RequestPart MultipartFile file, @PathVariable Long id) throws IOException {
        Optional<Item> isFoundItem = itemService.findItemEntityById(id);

        if (isFoundItem.isPresent()) {
            Item savedItem = itemService.updateItemEntity(itemService.stringItemToJson(itemString), file, id);
            return new ResponseEntity<>(savedItem, HttpStatus.OK);
        }

        return new ResponseEntity<>("The specified item does not exist",HttpStatus.BAD_REQUEST);
    }

    //update price
    @PutMapping(value="/{id}/price")
    public ResponseEntity<?> updateItemEntityPriceById(@RequestBody Long price ,@PathVariable Long id) {
        Optional<Item> isFoundItem = itemService.findItemEntityById(id);

        if (isFoundItem.isPresent()) {
            Item updatedItem = itemService.updatePriceOfItemEntity(price, id);

            return new ResponseEntity<>(updatedItem, HttpStatus.OK);
        }

        return new ResponseEntity<>("The specified item does not exist", HttpStatus.BAD_REQUEST);
    }

    //update name
    @PutMapping(value="/{id}/name")
    public ResponseEntity<?> updateItemEntityNameById(@RequestBody String name ,@PathVariable Long id) {
        Optional<Item> isFoundItem = itemService.findItemEntityById(id);

        if (isFoundItem.isPresent()) {
            Item updatedItem = itemService.updateNameOfItemEntity(name, id);

            return new ResponseEntity<>(updatedItem, HttpStatus.OK);
        }

        return new ResponseEntity<>("The specified item does not exist", HttpStatus.BAD_REQUEST);
    }

    //update saldo
    @PutMapping(value="/{id}/saldo")
    public ResponseEntity<?> updateItemEntitySaldoById(@RequestBody Long price ,@PathVariable Long id) {
        Optional<Item> isFoundItem = itemService.findItemEntityById(id);

        if (isFoundItem.isPresent()) {
            Item updatedItem = itemService.updateSaldoOfItemEntity(price, id);

            return new ResponseEntity<>(updatedItem, HttpStatus.OK);
        }

        return new ResponseEntity<>("The specified item does not exist", HttpStatus.BAD_REQUEST);
    }

    //TODO: SKALL KOMBINERA CUSTOMER, ORDER OCH ITEM
    //buy item
    @PostMapping("/buy")
    public ResponseEntity<?> buyItem(@RequestBody Object whatTheHellShouldBeInsideTheRequestBody) {
        return new ResponseEntity<>("NEED TO IMPLEMENT THIS ENDPOINT COMBINE CUSTOMER, ORDER & ITEM", HttpStatus.NOT_FOUND);
    }


}
