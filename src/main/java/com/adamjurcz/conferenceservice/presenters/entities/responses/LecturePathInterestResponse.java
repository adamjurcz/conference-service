package com.adamjurcz.conferenceservice.presenters.entities.responses;

import lombok.Value;

import java.util.TreeMap;

@Value
public class LecturePathInterestResponse {
    TreeMap<Integer, Double> lecturePathInterest;
}
