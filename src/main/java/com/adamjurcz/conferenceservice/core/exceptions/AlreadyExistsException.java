package com.adamjurcz.conferenceservice.core.exceptions;

public class AlreadyExistsException extends DomainException{
    public AlreadyExistsException(String message) {
        super(message);
    }
    public AlreadyExistsException(String message, Object... args){
        super(String.format(message,args));
    }
}
