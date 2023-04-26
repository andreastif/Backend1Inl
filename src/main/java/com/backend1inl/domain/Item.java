package com.backend1inl.domain;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
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
    private Long saldo;
    private LocalDateTime created;
    private LocalDateTime lastUpdated;
    private String URI;

}
