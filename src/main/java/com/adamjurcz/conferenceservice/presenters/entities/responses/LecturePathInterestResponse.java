package com.adamjurcz.conferenceservice.presenters.entities.responses;

import lombok.Value;

import java.io.Serializable;
import java.util.TreeMap;

@Value
public class LecturePathInterestResponse implements Serializable {
    TreeMap<Integer, Double> lecturePathInterest;
}
