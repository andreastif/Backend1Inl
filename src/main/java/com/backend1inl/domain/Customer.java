package com.backend1inl.domain;


import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class Customer {

    private Long id;

    @NotEmpty(message = "First Name Is Mandatory")
    @Size(min = 3, message = "At least 3 Letters for first name")
    @Pattern(regexp="^[A-Öa-ö]*$", message = "Only Letters for first name")
    private String firstName;

    @NotEmpty(message = "Last name is mandatory.")
    @Size(min = 3, message = "At least 3 Letters for last name")
    @Pattern(regexp="^[A-Öa-ö]*$",message = "Only Letters for first name")
    private String lastName;

    @NotEmpty(message = "Social security number is mandatory.")
    @Size(min = 10, max = 12, message = "Social security number needs to be 10 or 12 digits")
    private String ssn;

    private LocalDate created;
    private LocalDate lastUpdated;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return Objects.equals(id, customer.id) && Objects.equals(firstName, customer.firstName) && Objects.equals(lastName, customer.lastName) && Objects.equals(ssn, customer.ssn) && Objects.equals(created, customer.created) && Objects.equals(lastUpdated, customer.lastUpdated);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, ssn, created, lastUpdated);
    }
}
