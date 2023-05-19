package com.adamjurcz.conferenceservice.presenters.config;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class ExceptionDetails {
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private LocalDateTime timestamp;
    private String message;
    private HttpStatus httpStatus;

    public ExceptionDetails(String message, HttpStatus httpStatus){
        timestamp = LocalDateTime.now();
        this.message = message;
        this.httpStatus = httpStatus;
    }


}
