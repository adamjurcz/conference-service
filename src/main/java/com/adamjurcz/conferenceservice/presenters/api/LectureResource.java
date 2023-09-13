package com.adamjurcz.conferenceservice.presenters.api;

import com.adamjurcz.conferenceservice.presenters.entities.responses.LectureInterestResponse;
import com.adamjurcz.conferenceservice.presenters.entities.responses.LecturePathInterestResponse;
import com.adamjurcz.conferenceservice.presenters.entities.responses.LectureResponse;
import com.adamjurcz.conferenceservice.presenters.entities.requests.UserProfileRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(LectureResource.BASE_URL)
public interface LectureResource {
    String BASE_URL = "/api/v1/lectures";

    @GetMapping("/interests")
    ResponseEntity<List<LectureInterestResponse>> getLectureInterest();

    @GetMapping("/interests/paths")
    ResponseEntity<LecturePathInterestResponse> getLecturePathInterest();

    @GetMapping
    ResponseEntity<List<LectureResponse>> getAllLectures();

    @Transactional
    @PostMapping("/{lectureId}/reservations")
    ResponseEntity<LectureResponse> createLectureReservation(@PathVariable Integer lectureId,
                                                             @RequestBody UserProfileRequest userProfileRequest);
    @Transactional
    @DeleteMapping("/{lectureId}/reservations/{login}")
    ResponseEntity<Void> deleteLectureReservation(@PathVariable Integer lectureId, @PathVariable String login);



}
