package com.adamjurcz.conferenceservice.core.usecases.lecture;

import com.adamjurcz.conferenceservice.core.domain.Identity;
import com.adamjurcz.conferenceservice.core.domain.Lecture;
import com.adamjurcz.conferenceservice.core.domain.UserProfile;
import com.adamjurcz.conferenceservice.core.exceptions.*;
import com.adamjurcz.conferenceservice.core.usecases.UseCase;
import com.adamjurcz.conferenceservice.core.usecases.userprofile.UserProfileRepository;
import lombok.Value;

import java.util.concurrent.CompletableFuture;


public class CreateLectureReservationUseCase extends UseCase<CreateLectureReservationUseCase.Input, CreateLectureReservationUseCase.Output>{
    private UserProfileRepository userProfileRepository;
    private LectureRepository lectureRepository;
    private EmailNotificationService emailNotificationService;

    public CreateLectureReservationUseCase(UserProfileRepository userProfileRepository,
                                           LectureRepository lectureRepository,
                                           EmailNotificationService emailNotificationService) {
        this.userProfileRepository = userProfileRepository;
        this.lectureRepository = lectureRepository;
        this.emailNotificationService = emailNotificationService;
    }

    @Override
    public Output execute(Input input) throws DomainException {
        UserProfile userProfile = userProfileRepository.getByLogin(input.getLogin())
                .orElse(UserProfile.newInstance(input.getLogin(), input.getEmail(), null, false));

        if(!input.getEmail().equals(userProfile.getEmail())){
            throw new AlreadyExistsException("Login: %s jest juz uzywany!", input.getLogin());
        }

        Lecture lecture = lectureRepository.getById(input.getLecture_id())
                .orElseThrow(()->new NotFoundException("Prelekcja o id: %s nie istnieje!", input.getLecture_id().getValue()));

        if(lecture.hasMaximumListeners()){
            throw new MaximumListenersInLectureException("Prelekcja o id: %s juz ma limit osob!", input.getLecture_id().getValue());
        }

        if(userProfile.isHourAlreadyReserved(lecture)){
            throw new UserAlreadyReservedHourException("Uzytkownik: %s juz zarezerwowal te godzine!", input.getLogin());
        }
        userProfile.getLectures().add(lecture);
        userProfileRepository.persist(userProfile);

        CompletableFuture.runAsync(() -> {
            emailNotificationService.sendNotification(input.getEmail(), lecture);
        });

        return new Output(lecture);
    }

    @Value
    public static class Input implements UseCase.Input{
        String login;
        String email;
        Identity lecture_id;
    }

    @Value
    public static class Output implements UseCase.Output{
        Lecture lecture;
    }
}
