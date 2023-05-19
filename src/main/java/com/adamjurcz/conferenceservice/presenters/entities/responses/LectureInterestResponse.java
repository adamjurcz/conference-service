package com.adamjurcz.conferenceservice.presenters.entities.responses;

import lombok.Value;

import java.io.Serializable;
import java.util.TreeMap;

@Value
public class LectureInterestResponse implements Serializable {
    TreeMap<LectureResponse, Double> lectureInterest;
}
