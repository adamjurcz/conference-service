package com.adamjurcz.conferenceservice.core.usecases.lecture;

import com.adamjurcz.conferenceservice.core.domain.Identity;
import com.adamjurcz.conferenceservice.core.domain.Lecture;
import com.adamjurcz.conferenceservice.core.domain.UserProfile;
import com.adamjurcz.conferenceservice.core.exceptions.NotFoundException;
import com.adamjurcz.conferenceservice.core.usecases.UseCase;
import com.adamjurcz.conferenceservice.core.usecases.userprofile.UserProfileRepository;
import lombok.Value;

public class DeleteLectureReservationUseCase extends UseCase<DeleteLectureReservationUseCase.Input, DeleteLectureReservationUseCase.Output> {
    private UserProfileRepository userProfileRepository;

    public DeleteLectureReservationUseCase(UserProfileRepository userProfileRepository) {
        this.userProfileRepository = userProfileRepository;
    }

    @Override
    public Output execute(Input input) {
        UserProfile userProfile = userProfileRepository.getByLogin(input.getLogin())
                .orElseThrow(()->new NotFoundException("Login: %s nie istnieje", input.getLogin()));

        Lecture userLecture = userProfile.getLectures()
                .stream()
                .filter(lecture -> lecture.getId().equals(input.getLecture_id()))
                .findFirst()
                .orElseThrow(()->new NotFoundException("Prelekcja o id: %s nie jest zarezerwowana przez uzytkownika!",
                        input.getLecture_id().getValue()));
        userProfile.getLectures().remove(userLecture);

        userProfileRepository.persist(userProfile);
        return new Output();
    }

    @Value
    public static class Input implements UseCase.Input{
        String login;
        Identity lecture_id;
    }

    @Value
    public static class Output implements UseCase.Output{
    }
}
