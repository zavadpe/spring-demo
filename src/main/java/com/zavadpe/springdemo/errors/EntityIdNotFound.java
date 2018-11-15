package com.zavadpe.springdemo.errors;

public class EntityIdNotFound extends RuntimeException {

    private static final String MESSAGE = "%s with ID %s not found";

    public EntityIdNotFound(String entity, Long id) {
        super(String.format(MESSAGE, entity, id));
    }
}
