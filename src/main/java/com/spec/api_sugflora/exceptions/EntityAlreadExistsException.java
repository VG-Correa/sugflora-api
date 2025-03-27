package com.spec.api_sugflora.exceptions;

public class EntityAlreadExistsException extends RuntimeException {
    public EntityAlreadExistsException(String message) {
        super(message);
    }
}
