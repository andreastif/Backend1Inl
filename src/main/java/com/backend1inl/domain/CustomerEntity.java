package com.backend1inl.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "Customer")
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

    private LocalDateTime created;
    private LocalDateTime lastUpdated;
}
