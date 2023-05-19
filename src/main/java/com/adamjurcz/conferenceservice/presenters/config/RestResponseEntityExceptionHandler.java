package com.adamjurcz.conferenceservice.presenters.config;

import com.adamjurcz.conferenceservice.core.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = InvalidDataException.class)
    public ResponseEntity<Object> handleInvalidDataException(InvalidDataException exception){
        ExceptionDetails exceptionDetails = new ExceptionDetails(exception.getMessage(), HttpStatus.NOT_ACCEPTABLE);
        return new ResponseEntity<>(exceptionDetails, HttpStatus.NOT_ACCEPTABLE);
    }

    @ExceptionHandler(value = AlreadyExistsException.class)
    public ResponseEntity<Object> handleAlreadyInUseException(AlreadyExistsException exception){
        ExceptionDetails exceptionDetails = new ExceptionDetails(exception.getMessage(), HttpStatus.CONFLICT);
        return new ResponseEntity<>(exceptionDetails, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(value = MaximumListenersInLectureException.class)
    public ResponseEntity<Object> handleMaximumListenersInLectureException(MaximumListenersInLectureException exception){
        ExceptionDetails exceptionDetails = new ExceptionDetails(exception.getMessage(), HttpStatus.CONFLICT);
        return new ResponseEntity<>(exceptionDetails, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(value = NotFoundException.class)
    public ResponseEntity<Object> handleNotFoundException(NotFoundException exception){
        ExceptionDetails exceptionDetails = new ExceptionDetails(exception.getMessage(), HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(exceptionDetails, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = UserAlreadyReservedHourException.class)
    public ResponseEntity<Object> handleUserAlreadyReservedHour(UserAlreadyReservedHourException exception){
        ExceptionDetails exceptionDetails = new ExceptionDetails(exception.getMessage(), HttpStatus.NOT_ACCEPTABLE);
        return new ResponseEntity<>(exceptionDetails, HttpStatus.NOT_ACCEPTABLE);
    }
}
