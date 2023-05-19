package com.adamjurcz.conferenceservice.presenters.entities.responses;

import lombok.Value;

import java.util.TreeMap;

@Value
public class LectureInterestResponse {
    TreeMap<LectureResponse, Double> lectureInterest;
}
