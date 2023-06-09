package com.adamjurcz.conferenceservice.presenters.entities.responses;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Value;

import java.io.Serializable;
import java.time.LocalDateTime;

@Value
public class LectureResponse implements Comparable<LectureResponse>, Serializable {
    Integer id;
    String main_subject;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    LocalDateTime start_time;
    Integer path_number;

    @Override
    public int compareTo(LectureResponse o) {
        return Integer.compare(this.id, o.getId());
    }
}