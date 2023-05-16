package com.adamjurcz.conferenceservice.core.usecases.lecture;

import com.adamjurcz.conferenceservice.core.domain.Identity;
import com.adamjurcz.conferenceservice.core.domain.Lecture;
import com.adamjurcz.conferenceservice.core.domain.UserProfile;
import com.adamjurcz.conferenceservice.core.exceptions.LoginAlreadyInUseException;
import com.adamjurcz.conferenceservice.core.exceptions.MaximumListenersInLectureException;
import com.adamjurcz.conferenceservice.core.exceptions.NotFoundException;
import com.adamjurcz.conferenceservice.core.exceptions.UserAlreadyReservedHourException;
import com.adamjurcz.conferenceservice.core.usecases.UseCase;
import com.adamjurcz.conferenceservice.core.usecases.userprofile.UserProfileRepository;
import lombok.Value;


public class CreateLectureReservationUseCase extends UseCase<CreateLectureReservationUseCase.Input, CreateLectureReservationUseCase.Output>{
    private UserProfileRepository userProfileRepository;
    private LectureRepository lectureRepository;

    public CreateLectureReservationUseCase(UserProfileRepository userProfileRepository, LectureRepository lectureRepository) {
        this.userProfileRepository = userProfileRepository;
        this.lectureRepository = lectureRepository;
    }

    @Override
    public Output execute(Input input) {
        UserProfile userProfile = userProfileRepository.getByLogin(input.getLogin())
                .orElse(UserProfile.newInstance(input.getLogin(), input.getEmail()));

        if(!input.getEmail().equals(userProfile.getEmail())){
            throw new LoginAlreadyInUseException("Login: %s jest juz uzywany!", input.getLogin());
        }

        Lecture lecture = lectureRepository.getById(input.getLecture_id())
                .orElseThrow(()->new NotFoundException("Prelekcja o id: %s nie istnieje!", input.getLecture_id()));

        if(lecture.hasMaximumListeners()){
            throw new MaximumListenersInLectureException("Prelekcja o id: %s juz ma limit osob!", input.getLecture_id());
        }

        if(userProfile.isHourAlreadyReserved(lecture)){
            throw new UserAlreadyReservedHourException("Uzytkownik: %s juz zarezerwowal te godzine!", input.getLogin());
        }
        //TODO mozna tez sprawdzic czy uzytkownik wysyla ta sama rezerwacje (o tym samym id co ma w liscie)

        userProfile.getLectures().add(lecture);
        userProfileRepository.persist(userProfile);
        //TODO wysylka maila
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
