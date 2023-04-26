package com.backend1inl.domain;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Table(name = "Orders")
public class OrderEntity {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(optional = false) // Order måste va bunden till kund, får ej va null på FK!
    @JoinColumn(name = "customer_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private CustomerEntity customerEntity;

    private LocalDate created;
    private LocalDate lastUpdated;
}
