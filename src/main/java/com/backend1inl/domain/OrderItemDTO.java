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
