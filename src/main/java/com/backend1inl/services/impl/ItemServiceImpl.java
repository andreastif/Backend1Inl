package com.backend1inl.services.impl;

import com.backend1inl.domain.Item;
import com.backend1inl.domain.ItemEntity;
import com.backend1inl.exception.InvalidItemNameException;
import com.backend1inl.exception.InvalidPriceException;
import com.backend1inl.exception.NoSuchItemException;
import com.backend1inl.repositories.ItemRepository;
import com.backend1inl.services.ItemService;
import com.backend1inl.utils.DeleteResponse;
import com.backend1inl.utils.ItemImageUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.sql.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class ItemServiceImpl implements ItemService {

    //TODO: SAKNAR ENHETSTESTER

    private final ItemRepository itemRepository;

    @Autowired
    public ItemServiceImpl(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    @Override
    public Item createNewItemEntity(Item item, MultipartFile file) throws IOException {
        ItemEntity itemEntityToBeSaved = itemToItemEntity(item, file);

        itemEntityToBeSaved.setCreated(LocalDate.now());
        itemEntityToBeSaved.setLastUpdated(LocalDate.now());

        ItemEntity savedItemEntity = itemRepository.save(itemEntityToBeSaved);
        return itemEntityToItem(savedItemEntity);
    }

    @Override
    public Item updateItemEntity(Item item, MultipartFile file, Long id) throws IOException {

        /*
        Flödesbeskrivning:
        För att konsumenten av APIN skall kunna uppdatera ett item sker följande:
        1a. Konsumenten anropar updateItemEntityById i Controllern.
        1b. Om Item EJ är skapat returneras Bad Request. Du KAN EJ uppdatera ett item som _INTE_ finns.
        1c. Om Item FINNS:
        1d. APIt skall ha serverat ut all befintlig data till konsumenten och förpopulerar alla inputfält enligt befintlig data.
        1e. Det är nu upp till konsumenten att välja vilken information som skall skickas tillbaka (nytt pris, ny bild, nytt namn, nytt balance)
        antingen med det som redan är förpopulerat eller med helt nya värden.
        2. Eftersom vi FÖRUTSÄTTER att KORREKT och RIKTIG data skickas från konsumenten tillbaka till APIt innebär det att vi kan använda GETTERS på alla
        världen som kan uppdateras (namn, pris, balance).
         */

        var itemFromDB = findItemEntityById(id);

        if (itemFromDB.isEmpty()) {
            throw new NoSuchItemException("The specified item does not exist");
        } else {
            ItemEntity itemEntityToBeSaved = itemToItemEntity(itemFromDB.get(), file);

            itemEntityToBeSaved.setId(itemFromDB.get().getId());

            itemEntityToBeSaved.setName(item.getName());
            itemEntityToBeSaved.setPrice(item.getPrice());
            itemEntityToBeSaved.setBalance(item.getBalance());
            itemEntityToBeSaved.setCreated(itemFromDB.get().getCreated());
            itemEntityToBeSaved.setLastUpdated(LocalDate.now());

            ItemEntity savedItemEntity = itemRepository.save(itemEntityToBeSaved);

            return itemEntityToItem(savedItemEntity);
        }
    }

    @Override
    public Item updatePriceOfItemEntity(Long price, Long id) {
        if (price < 1L) {
            throw new InvalidPriceException("Item price cannot be below 1");
        } else if (itemRepository.findById(id).isEmpty()) {
            throw new NoSuchItemException("Specified item could not be found");
        } else {
            ItemEntity itemFromDB = itemRepository.findById(id).get();
            itemFromDB.setPrice(price);
            itemFromDB.setLastUpdated(LocalDate.now());
            return itemEntityToItem(itemRepository.save(itemFromDB));
        }
    }

    @Override
    public Item updateNameOfItemEntity(String name, Long id) {

        if (name == null || name.isEmpty() || name.isBlank()) {
            throw new InvalidItemNameException("The name cannot be null, empty or blank!");
        }
        if (itemRepository.findById(id).isEmpty()) {
            throw new NoSuchItemException("Specified item could not be found");
        }
        {
            ItemEntity itemFromDB = itemRepository.findById(id).get();
            itemFromDB.setName(name);
            itemFromDB.setLastUpdated(LocalDate.now());
            return itemEntityToItem(itemRepository.save(itemFromDB));
        }
    }

    @Override
    public Item updateBalanceOfItemEntity(Long balance, Long id) {

        if (itemRepository.findById(id).isEmpty()) {
            throw new NoSuchItemException("Specified item could not be found");
        } else {
            ItemEntity itemFromDB = itemRepository.findById(id).get();
            itemFromDB.setBalance(balance);
            itemFromDB.setLastUpdated(LocalDate.now());
            return itemEntityToItem(itemRepository.save(itemFromDB));
        }
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
    public Optional<Item> findItemEntityById(Long id) {
        var fetchedItemEntity = itemRepository.findById(id);
        return fetchedItemEntity.map(this::itemEntityToItem);
    }

    @Override
    public ItemEntity itemToItemEntity(Item item, MultipartFile file) throws IOException {
        return ItemEntity
                .builder()
                .name(item.getName())
                .price(item.getPrice())
                .balance(item.getBalance())
                .created(item.getCreated())
                .lastUpdated(item.getLastUpdated())
                .imgData(ItemImageUtils.compressImage(file.getBytes()))
                .build();
    }

    @Override
    public Item itemEntityToItem(ItemEntity itemEntity) {
        return Item
                .builder()
                .id(itemEntity.getId())
                .price(itemEntity.getPrice())
                .balance(itemEntity.getBalance())
                .created(itemEntity.getCreated())
                .lastUpdated(itemEntity.getLastUpdated())
                .URI(itemURIBuilder(itemEntity.getId()))
                .name(itemEntity.getName())
                .build();
    }


    @Override
    public DeleteResponse deleteItemEntityById(Long id) {
        var match = itemRepository.findById(id);
        if (match.isPresent()) {
            itemRepository.deleteById(match.get().getId());
            return new DeleteResponse(true);
        }
        return new DeleteResponse(false);
    }


    @Override
    public String itemURIBuilder(Long id) {
        return ServletUriComponentsBuilder.fromCurrentContextPath().path("/items/").path(String.valueOf(id)).path("/img").toUriString();
    }
}
