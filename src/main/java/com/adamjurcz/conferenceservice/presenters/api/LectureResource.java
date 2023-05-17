package com.adamjurcz.conferenceservice.presenters.api;

import com.adamjurcz.conferenceservice.core.domain.Lecture;
import com.adamjurcz.conferenceservice.presenters.entities.requests.UserProfileRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(LectureResource.BASE_URL)
public interface LectureResource {
    String BASE_URL = "/api/v1/lectures";

    @PostMapping("/{lectureId}/reservations")
    ResponseEntity<Lecture> createLectureReservation(@PathVariable Integer lectureId, @RequestBody UserProfileRequest userProfileRequest);

    @DeleteMapping("/{lectureId}/reservations/{userId}")
    ResponseEntity<Void> deleteLectureReservation(@PathVariable Integer lectureId, @PathVariable Integer userId);


}
