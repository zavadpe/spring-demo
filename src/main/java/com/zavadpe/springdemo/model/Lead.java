package com.zavadpe.springdemo.model;

import lombok.Data;

import javax.persistence.Entity;
import java.sql.Date;

@Data
@Entity
public class Lead extends Person {

    protected boolean distributed;

    public Lead() {}

    public Lead(String name, String surname, String email, String phone, Address address, Date born, boolean distributed) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.born = born;
        this.distributed = distributed;
    }

    public Lead(String name, String surname, String email, String phone, Address address, Date born) {
        this(name, surname, email, phone, address, born, false);
    }
}
