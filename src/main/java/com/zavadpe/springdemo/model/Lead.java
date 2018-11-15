package com.zavadpe.springdemo.model;

import lombok.Data;

import javax.persistence.Entity;
import java.sql.Date;

@Data
@Entity
public class Lead extends Person {

    protected Boolean distributed = false;

    public Lead() {}

    public Lead(String name, String surname, String email, String phone, Address address, Date born) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.born = born;
    }
}
