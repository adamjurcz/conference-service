package com.adamjurcz.conferenceservice.presenters.api;

import com.adamjurcz.conferenceservice.core.domain.Identity;
import com.adamjurcz.conferenceservice.core.domain.Lecture;
import com.adamjurcz.conferenceservice.core.usecases.lecture.CreateLectureReservationUseCase;
import com.adamjurcz.conferenceservice.core.usecases.lecture.DeleteLectureReservationUseCase;
import com.adamjurcz.conferenceservice.presenters.entities.requests.UserProfileRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class LectureController implements LectureResource {
    private CreateLectureReservationUseCase createLectureReservationUseCase;
    private DeleteLectureReservationUseCase deleteLectureReservationUseCase;

    public LectureController(CreateLectureReservationUseCase createLectureReservationUseCase,
                             DeleteLectureReservationUseCase deleteLectureReservationUseCase) {
        this.createLectureReservationUseCase = createLectureReservationUseCase;
        this.deleteLectureReservationUseCase = deleteLectureReservationUseCase;
    }

    @Override
    public ResponseEntity<Lecture> createLectureReservation(Integer lectureId, UserProfileRequest userProfileRequest) {
        Lecture lecture = createLectureReservationUseCase.execute(new CreateLectureReservationUseCase.Input(userProfileRequest.getLogin(),
                userProfileRequest.getEmail(), new Identity(lectureId))).getLecture();

        return new ResponseEntity<>(lecture, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<Void> deleteLectureReservation(Integer lectureId, String login) {
        deleteLectureReservationUseCase.execute(new DeleteLectureReservationUseCase.Input(login, new Identity(lectureId)));
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
