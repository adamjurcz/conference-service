package com.adamjurcz.conferenceservice.presenters.api;

import com.adamjurcz.conferenceservice.core.domain.Lecture;
import com.adamjurcz.conferenceservice.core.domain.UserProfile;
import com.adamjurcz.conferenceservice.core.usecases.userprofile.EditUserEmailUseCase;
import com.adamjurcz.conferenceservice.core.usecases.userprofile.GetAllUsersUseCase;
import com.adamjurcz.conferenceservice.core.usecases.userprofile.GetUserReservationsUseCase;
import com.adamjurcz.conferenceservice.presenters.entities.requests.UserProfileRequest;
import com.adamjurcz.conferenceservice.presenters.entities.requests.UserProfileResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserProfileController implements UserProfileResource{
    private GetAllUsersUseCase getAllUsersUseCase;
    private EditUserEmailUseCase editUserEmailUseCase;
    private GetUserReservationsUseCase getUserReservationsUseCase;

    public UserProfileController(GetAllUsersUseCase getAllUsersUseCase,
                                 EditUserEmailUseCase editUserEmailUseCase,
                                 GetUserReservationsUseCase getUserReservationsUseCase) {
        this.getAllUsersUseCase = getAllUsersUseCase;
        this.editUserEmailUseCase = editUserEmailUseCase;
        this.getUserReservationsUseCase = getUserReservationsUseCase;
    }

    @Override
    public ResponseEntity<List<UserProfileResponse>> getAllUsers() {
        List<UserProfileResponse> userProfiles = getAllUsersUseCase
                .execute(new GetAllUsersUseCase.Input())
                .getUserProfiles()
                .stream()
                .map(userProfile -> new UserProfileResponse(userProfile.getLogin(), userProfile.getEmail()))
                .collect(Collectors.toList());
        return new ResponseEntity<>(userProfiles, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<Lecture>> getUserReservations(String login) {
        List<Lecture> lectures = getUserReservationsUseCase.execute(new GetUserReservationsUseCase.Input(login)).getLectures();
        return new ResponseEntity<>(lectures, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<UserProfile> editUserEmail(String newEmail, UserProfileRequest oldAccount) {
        UserProfile userProfile = editUserEmailUseCase
                .execute(new EditUserEmailUseCase.Input(oldAccount.getLogin(), newEmail)).getUserProfile();
        return new ResponseEntity<>(userProfile, HttpStatus.OK);
    }
}
