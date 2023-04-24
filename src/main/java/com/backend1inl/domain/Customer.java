package com.backend1inl.domain;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Customer {

    // Denna behöver väl inte ha ett @Id och @GeneratedValue? Bara Entity?
    private Long id;
    private String firstName;
    private String lastName;
    private String ssn;

}
