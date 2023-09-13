package com.adamjurcz.conferenceservice.presenters.entities.responses;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Value;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.TreeMap;

@Value
public class LectureInterestResponse implements Serializable {
    Integer id;
    String main_subject;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    LocalDateTime start_time;
    Integer path_number;
    Double interest;
}
