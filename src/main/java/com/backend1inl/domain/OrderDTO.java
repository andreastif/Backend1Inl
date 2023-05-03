package com.backend1inl.domain;



import lombok.*;

import java.time.LocalDate;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@Builder
public class OrderDTO {
    private Long id;
    private LocalDate created;
    private LocalDate lastUpdated;
    private List<Item> items;
}
