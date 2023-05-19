package com.adamjurcz.conferenceservice.presenters.entities.responses;

import com.adamjurcz.conferenceservice.core.domain.Lecture;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Value;

import java.time.LocalDateTime;

@Value
public class LectureResponse implements Comparable<LectureResponse>{
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