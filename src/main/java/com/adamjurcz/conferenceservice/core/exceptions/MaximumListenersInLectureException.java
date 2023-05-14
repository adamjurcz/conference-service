package com.adamjurcz.conferenceservice.core.exceptions;

public class MaximumListenersInLectureException extends DomainException{
    public MaximumListenersInLectureException(String message) {
        super(message);
    }

    public MaximumListenersInLectureException(String message, Object... args){
        super(String.format(message, args));
    }
}
