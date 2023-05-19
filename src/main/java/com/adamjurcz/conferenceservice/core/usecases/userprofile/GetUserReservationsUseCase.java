package com.adamjurcz.conferenceservice.core.usecases.userprofile;

import com.adamjurcz.conferenceservice.core.domain.Lecture;
import com.adamjurcz.conferenceservice.core.domain.UserProfile;
import com.adamjurcz.conferenceservice.core.exceptions.NotFoundException;
import com.adamjurcz.conferenceservice.core.usecases.UseCase;
import lombok.Value;

import java.util.List;

public class GetUserReservationsUseCase extends UseCase<GetUserReservationsUseCase.Input, GetUserReservationsUseCase.Output> {
    private UserProfileRepository userProfileRepository;

    public GetUserReservationsUseCase(UserProfileRepository userProfileRepository) {
        this.userProfileRepository = userProfileRepository;
    }

    @Override
    public Output execute(Input input){
        UserProfile userProfile = userProfileRepository.getByLogin(input.getLogin())
                .orElseThrow(()-> new NotFoundException("Uzytkownik o loginie: %s nie istnieje", input.getLogin()));

        return new Output(userProfile.getLectures());
    }

    @Value
    public static class Input implements UseCase.Input{
        String login;
    }

    @Value
    public static class Output implements UseCase.Output{
        List<Lecture> lectures;
    }
}
