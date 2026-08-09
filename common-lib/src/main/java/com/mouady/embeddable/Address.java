package com.mouady.embeddable;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Embeddable
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Address {

    private String street;
    private String postalCode;
}
