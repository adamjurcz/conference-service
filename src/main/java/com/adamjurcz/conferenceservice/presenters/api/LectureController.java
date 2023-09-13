package com.adamjurcz.conferenceservice.presenters.api;

import com.adamjurcz.conferenceservice.core.domain.Identity;
import com.adamjurcz.conferenceservice.core.domain.Lecture;
import com.adamjurcz.conferenceservice.core.usecases.lecture.*;
import com.adamjurcz.conferenceservice.presenters.entities.responses.LectureInterestResponse;
import com.adamjurcz.conferenceservice.presenters.entities.responses.LecturePathInterestResponse;
import com.adamjurcz.conferenceservice.presenters.entities.responses.LectureResponse;
import com.adamjurcz.conferenceservice.presenters.entities.requests.UserProfileRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class LectureController implements LectureResource {
    private CreateLectureReservationUseCase createLectureReservationUseCase;
    private DeleteLectureReservationUseCase deleteLectureReservationUseCase;
    private GetLectureInterestUseCase getLectureInterestUseCase;
    private GetLecturePathInterestUseCase getLecturePathInterestUseCase;
    private GetAllLecturesUseCase getAllLecturesUseCase;

    public LectureController(CreateLectureReservationUseCase createLectureReservationUseCase,
                             DeleteLectureReservationUseCase deleteLectureReservationUseCase,
                             GetLectureInterestUseCase getLectureInterestUseCase,
                             GetLecturePathInterestUseCase getLecturePathInterestUseCase,
                             GetAllLecturesUseCase getAllLecturesUseCase) {
        this.createLectureReservationUseCase = createLectureReservationUseCase;
        this.deleteLectureReservationUseCase = deleteLectureReservationUseCase;
        this.getLectureInterestUseCase = getLectureInterestUseCase;
        this.getLecturePathInterestUseCase = getLecturePathInterestUseCase;
        this.getAllLecturesUseCase = getAllLecturesUseCase;
    }

    @Override
    public ResponseEntity<List<LectureInterestResponse>> getLectureInterest() {
        Map<Lecture, Double> lectureDoubleMap = getLectureInterestUseCase.execute(new GetLectureInterestUseCase.Input())
                .getLectureDoubleMap();

        List<LectureInterestResponse> lectureInterestResponses = new ArrayList<>();
        for(Map.Entry<Lecture, Double> entry: lectureDoubleMap.entrySet()){
            Lecture lecture = entry.getKey();
            Double interest = entry.getValue();

            LectureInterestResponse lectureInterestResponse = new LectureInterestResponse(lecture.getId().getValue(), lecture.getMain_subject(),
                    lecture.getStart_time(), lecture.getPath_number(), interest);
            lectureInterestResponses.add(lectureInterestResponse);
        }
        Collections.sort(lectureInterestResponses, Comparator.comparing(LectureInterestResponse::getId));
        return new ResponseEntity<>(lectureInterestResponses, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<LecturePathInterestResponse> getLecturePathInterest() {
        Map<Integer, Double> integerDoubleMap = getLecturePathInterestUseCase.execute(new GetLecturePathInterestUseCase.Input())
                .getPathInterestMap();

        TreeMap<Integer, Double> integerDoubleTreeMap = new TreeMap<>(integerDoubleMap);
        return new ResponseEntity<>(new LecturePathInterestResponse(integerDoubleTreeMap), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<LectureResponse>> getAllLectures() {
        List<LectureResponse> lectures = getAllLecturesUseCase.execute(new GetAllLecturesUseCase.Input())
                .getLectures()
                .stream().map(lecture -> new LectureResponse(lecture.getId().getValue(), lecture.getMain_subject(),
                        lecture.getStart_time(), lecture.getPath_number())).collect(Collectors.toList());

        return new ResponseEntity<>(lectures, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<LectureResponse> createLectureReservation(Integer lectureId, UserProfileRequest userProfileRequest) {
        Lecture lecture = createLectureReservationUseCase.execute(new CreateLectureReservationUseCase.Input(userProfileRequest.getLogin(),
                userProfileRequest.getEmail(), new Identity(lectureId))).getLecture();

        LectureResponse lectureResponse = new LectureResponse(lecture.getId().getValue(), lecture.getMain_subject(),
                lecture.getStart_time(), lecture.getPath_number());

        return new ResponseEntity<>(lectureResponse, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<Void> deleteLectureReservation(Integer lectureId, String login) {
        deleteLectureReservationUseCase.execute(new DeleteLectureReservationUseCase.Input(login, new Identity(lectureId)));
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
