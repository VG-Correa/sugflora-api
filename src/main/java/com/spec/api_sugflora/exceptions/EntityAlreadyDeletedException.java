package com.spec.api_sugflora.exceptions;


public class EntityAlreadyDeletedException extends RuntimeException{

    public EntityAlreadyDeletedException(String message) {
        super(message);
    }

}
