package com.zavadpe.springdemo.errors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.NOT_FOUND, reason="Lead with given ID not found")
public class LeadNotFound extends RuntimeException {

    private static final String MESSAGE = "Lead with given ID not found";

    public LeadNotFound() {
        super(MESSAGE);
    }
}
