package com.adamjurcz.conferenceservice.presenters.api;

import com.adamjurcz.conferenceservice.core.domain.Lecture;
import com.adamjurcz.conferenceservice.presenters.entities.responses.LectureResponse;
import com.adamjurcz.conferenceservice.presenters.entities.responses.UserProfileIsAdminResponse;
import com.adamjurcz.conferenceservice.presenters.entities.responses.UserProfileResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(UserProfileResource.BASE_URL)
public interface UserProfileResource {
    String BASE_URL = "/api/v1/users";

    @GetMapping
    ResponseEntity<List<UserProfileResponse>> getAllUsers();

    @GetMapping("/{login}/reservations")
    ResponseEntity<List<LectureResponse>> getUserReservations(@PathVariable String login);

    @Transactional
    @PutMapping("/{login}/edits/{newEmail}")
    ResponseEntity<UserProfileResponse> editUserEmail(@PathVariable String login, @PathVariable String newEmail);

    @PostMapping("/{login}/{password}")
    ResponseEntity<UserProfileIsAdminResponse> isAdmin(@PathVariable String login, @PathVariable String password);

}
