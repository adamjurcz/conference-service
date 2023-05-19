package com.adamjurcz.conferenceservice.presenters.api;

import com.adamjurcz.conferenceservice.core.domain.Lecture;
import com.adamjurcz.conferenceservice.core.domain.UserProfile;
import com.adamjurcz.conferenceservice.core.usecases.userprofile.EditUserEmailUseCase;
import com.adamjurcz.conferenceservice.core.usecases.userprofile.GetAllUsersUseCase;
import com.adamjurcz.conferenceservice.core.usecases.userprofile.GetUserReservationsUseCase;
import com.adamjurcz.conferenceservice.presenters.entities.responses.LectureResponse;
import com.adamjurcz.conferenceservice.presenters.entities.responses.UserProfileResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

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
                .map(userProfile -> new UserProfileResponse(userProfile.getId().getValue(),
                        userProfile.getLogin(), userProfile.getEmail()))
                .collect(Collectors.toList());
        return new ResponseEntity<>(userProfiles, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<LectureResponse>> getUserReservations(String login) {
        List<LectureResponse> lecturesResponse = getUserReservationsUseCase
                .execute(new GetUserReservationsUseCase.Input(login))
                .getLectures()
                .stream()
                .map(lecture -> new LectureResponse(lecture.getId().getValue(), lecture.getMain_subject(),
                        lecture.getStart_time(), lecture.getPath_number()))
                .collect(Collectors.toList());
        return new ResponseEntity<>(lecturesResponse, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<UserProfileResponse> editUserEmail(String login, String newEmail) {
        UserProfile userProfile = editUserEmailUseCase
                .execute(new EditUserEmailUseCase.Input(login, newEmail)).getUserProfile();
        UserProfileResponse userProfileResponse = new UserProfileResponse(userProfile.getId().getValue(),
                userProfile.getLogin(), userProfile.getEmail());
        return new ResponseEntity<>(userProfileResponse, HttpStatus.OK);
    }
}
