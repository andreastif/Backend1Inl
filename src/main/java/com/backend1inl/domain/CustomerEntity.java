package com.backend1inl.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

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
@Table(name = "Customers")
public class CustomerEntity {

    @Id
    @GeneratedValue
    private Long id;

    @NotEmpty(message = "First Name Is Mandatory")
    @Size(min = 3, message = "At least 3 Letters for first name")
    @Pattern(regexp="^[A-Öa-ö]*$", message = "Only Letters for first name")
    private String firstName;

    @NotEmpty(message = "Last name is mandatory.")
    @Size(min = 3, message = "At least 3 Letters for last name")
    @Pattern(regexp="^[A-Öa-ö]*$", message = "Only Letters for first name")
    private String lastName;

    @NotEmpty(message = "Social security number is mandatory.")
    @Size(min = 10, max = 12, message = "Social security number needs to be 10 or 12 digits")
    private String ssn;

    @OneToMany(mappedBy = "customerEntity")
    private Set<OrderEntity> orders;

    private LocalDate created;
    private LocalDate lastUpdated;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CustomerEntity that = (CustomerEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(firstName, that.firstName) && Objects.equals(lastName, that.lastName) && Objects.equals(ssn, that.ssn) && Objects.equals(created, that.created) && Objects.equals(lastUpdated, that.lastUpdated);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, ssn, created, lastUpdated);
    }
}
