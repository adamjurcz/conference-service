package com.adamjurcz.conferenceservice.core.exceptions;

import java.io.Serializable;

public abstract class DomainException extends RuntimeException implements Serializable {
    public DomainException(String message){
        super(message);
    }
}
