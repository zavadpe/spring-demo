package com.zavadpe.springdemo.model;

import lombok.Data;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.validation.constraints.Size;

@Data
@Entity
public class Agent extends AbstractEntity {

    @Size(min = 2, message = "Name should have at least 2 characters")
    protected String name;
    @Embedded
    protected Address address;

    public Agent() {
    }

    public Agent(String name, Address address) {
        this.name = name;
        this.address = address;
    }
}
