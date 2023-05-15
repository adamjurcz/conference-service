package com.adamjurcz.conferenceservice.core.exceptions;

public class InvalidDataException extends DomainException{
    public InvalidDataException(String message) {
        super(message);
    }

    public InvalidDataException(String message, Object... args){
        super(String.format(message, args));
    }
}
