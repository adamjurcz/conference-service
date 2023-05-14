package com.adamjurcz.conferenceservice.core.exceptions;

public class LoginAlreadyInUseException extends DomainException{
    public LoginAlreadyInUseException(String message) {
        super(message);
    }
    public LoginAlreadyInUseException(String message, Object... args){
        super(String.format(message, args));
    }
}
