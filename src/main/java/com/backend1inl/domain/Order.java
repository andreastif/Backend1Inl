package com.backend1inl.domain;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class Order {
    private Long id;
    private LocalDate created;
    private LocalDate lastUpdated;

    // Todo: Collection av Items på order
}
