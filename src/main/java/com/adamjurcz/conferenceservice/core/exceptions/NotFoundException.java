package com.adamjurcz.conferenceservice.core.exceptions;

public class NotFoundException extends DomainException{
    public NotFoundException(String message) {
        super(message);
    }

    public NotFoundException(String message, Object... args){
        super(String.format(message, args));
    }
}
