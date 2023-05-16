package com.backend1inl.domain;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDate;
import java.util.Set;


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
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "orders_seq_generator")
    @SequenceGenerator(name = "orders_seq_generator", sequenceName = "orders_seq", allocationSize = 1)
    private Long id;

    @ManyToOne(optional = false) // Order måste va bunden till kund, får ej va null på FK!
    @JoinColumn(name = "customer_id")
    @OnDelete(action = OnDeleteAction.CASCADE) // Tas kund bort tas order bort i vårat fall
    private CustomerEntity customerEntity;

    private LocalDate created;
    private LocalDate lastUpdated;

    @OneToMany(mappedBy = "orderEntity")
    private Set<OrderItemEntity> orders;
}
