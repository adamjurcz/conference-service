package com.adamjurcz.conferenceservice.core.exceptions;

public abstract class DomainException extends RuntimeException{
    public DomainException(String message){
        super(message);
    }
}
