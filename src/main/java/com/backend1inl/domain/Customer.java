package com.backend1inl.domain;


import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Customer {

    private Long id;
    private String firstName;
    private String lastName;

    @NotEmpty(message = "Social security number is mandatory.")
    @Size(min = 10, max = 12, message = "Social security number needs to be 10 or 12 digits")
    private String ssn;

}
