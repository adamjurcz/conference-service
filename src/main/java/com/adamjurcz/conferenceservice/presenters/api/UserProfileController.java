package com.adamjurcz.conferenceservice.presenters.api;

import com.adamjurcz.conferenceservice.core.domain.UserProfile;
import com.adamjurcz.conferenceservice.core.usecases.userprofile.EditUserEmailUseCase;
import com.adamjurcz.conferenceservice.core.usecases.userprofile.GetAllUsersUseCase;
import com.adamjurcz.conferenceservice.core.usecases.userprofile.GetUserReservationsUseCase;
import com.adamjurcz.conferenceservice.core.usecases.userprofile.LogIntoAdminUseCase;
import com.adamjurcz.conferenceservice.presenters.entities.responses.LectureResponse;
import com.adamjurcz.conferenceservice.presenters.entities.responses.UserProfileIsAdminResponse;
import com.adamjurcz.conferenceservice.presenters.entities.responses.UserProfileResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserProfileController implements UserProfileResource{
    private final GetAllUsersUseCase getAllUsersUseCase;
    private final EditUserEmailUseCase editUserEmailUseCase;
    private final GetUserReservationsUseCase getUserReservationsUseCase;
    private final LogIntoAdminUseCase logIntoAdminUseCase;

    public UserProfileController(GetAllUsersUseCase getAllUsersUseCase,
                                 EditUserEmailUseCase editUserEmailUseCase,
                                 GetUserReservationsUseCase getUserReservationsUseCase, LogIntoAdminUseCase logIntoAdminUseCase) {
        this.getAllUsersUseCase = getAllUsersUseCase;
        this.editUserEmailUseCase = editUserEmailUseCase;
        this.getUserReservationsUseCase = getUserReservationsUseCase;
        this.logIntoAdminUseCase = logIntoAdminUseCase;
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

    @Override
    public ResponseEntity<UserProfileIsAdminResponse> isAdmin(String login, String password) {
        boolean isUserAdmin = logIntoAdminUseCase.execute(new LogIntoAdminUseCase.Input(login, password)).isCanLogIntoAdmin();
        return new ResponseEntity<>(new UserProfileIsAdminResponse(isUserAdmin), HttpStatus.OK);
    }
}
