package com.adamjurcz.conferenceservice.presenters.api;

import com.adamjurcz.conferenceservice.core.domain.Lecture;
import com.adamjurcz.conferenceservice.core.domain.UserProfile;
import com.adamjurcz.conferenceservice.presenters.entities.requests.UserProfileRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(UserProfileResource.BASE_URL)
public interface UserProfileResource {
    String BASE_URL = "/api/v1/users";

    @GetMapping
    ResponseEntity<List<UserProfile>> getAllUsers();

    @GetMapping("/{login}/reservations")
    ResponseEntity<List<Lecture>> getUserReservations(@PathVariable String login);

    @PutMapping("/{email}")
    ResponseEntity<UserProfile> editUserEmail(@PathVariable String email, @RequestBody UserProfileRequest oldAccount);

}
