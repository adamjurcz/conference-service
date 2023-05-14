package com.adamjurcz.conferenceservice.core.exceptions;

public class UserAlreadyReservedHourException extends DomainException{
    public UserAlreadyReservedHourException(String message) {
        super(message);
    }

    public UserAlreadyReservedHourException(String message, Object... objects){
        super(String.format(message, objects));
    }
}
