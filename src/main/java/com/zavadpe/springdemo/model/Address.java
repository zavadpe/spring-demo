package com.zavadpe.springdemo.model;

import lombok.Data;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotEmpty;

@Data
@Embeddable
public class Address {

    @NotEmpty
    private String city;
    @NotEmpty
    private String street;
    private String number;
    @NotEmpty
    private String postalCode;

    public Address() {}

    public Address(String city, String street, String number, String postalCode) {
        this.city = city;
        this.street = street;
        this.number = number;
        this.postalCode = postalCode;
    }
}
