package com.backend1inl.domain;

import lombok.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@Builder
public class Item {

    private Long id;
    private String name;
    private Long price;
    private Long balance;
    private LocalDateTime created;
    private LocalDateTime lastUpdated;
    private String URI;

}
