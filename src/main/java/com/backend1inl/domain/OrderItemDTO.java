package com.backend1inl.domain;

import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@Builder
public class OrderItemDTO {
    private Long id; // Syntetiska nyckeln i mappnings tabell "order_item"
    private Long orderId;
    private Long itemId;
    private int quantity;
}

// Måste nog vara såhär, annars får vi Oändlig loop igen, då OrderEntity innehåller set av itemEntity osv.
// Vi vill ju bara visa orderId i form av int samt ITEM objekt, samt hur många som las till, resten skiten vi i ????
// Vi kan ju bygga DTO hur vi vill, beroende på hur vi vill presentera information

// När vi slår på ...orders/<id>/products kanske vi kör en annan DTO för att visa en List<ItemEntity> för items kopplat till Order, SÅ vi inte får oändlig loop där också. --> "OrderItemsDTO"