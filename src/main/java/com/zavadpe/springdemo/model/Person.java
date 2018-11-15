package com.zavadpe.springdemo.model;

import lombok.Data;

import javax.persistence.Embedded;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.sql.Date;

@Data
@MappedSuperclass
public abstract class Person extends AbstractEntity {

    @Id
    @GeneratedValue
    protected Long id;
    @Size(min=2, message="Name should have at least 2 characters")
    protected String name;
    @Size(min=2, message="Surname should have at least 2 characters")
    protected String surname;
    protected String email;
    protected String phone;
    @Embedded
    protected Address address;
    @NotNull
    protected Date born;
}
