package com.backend1inl.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import net.minidev.json.annotate.JsonIgnore;
import org.hibernate.Hibernate;

import java.time.LocalDate;
import java.util.Objects;
import java.util.Set;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Table(name = "Items")
public class ItemEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "items_seq_generator")
    @SequenceGenerator(name = "items_seq_generator", sequenceName = "items_seq", allocationSize = 1)
    private Long id;
    @NotEmpty
    @NotBlank
    @Size(min = 1)
    private String name;
    @Min(1L)
    private Long price;
    private Long balance;
    private LocalDate created;
    private LocalDate lastUpdated;
    @Lob
    @Column(columnDefinition = "LONGBLOB")
    @JsonIgnore
    private byte[] imgData;

    @OneToMany(mappedBy = "itemEntity")
    private Set<OrderItemEntity> orders;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        ItemEntity that = (ItemEntity) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
