package com.adamjurcz.conferenceservice.core.usecases.userprofile;

import com.adamjurcz.conferenceservice.core.domain.UserProfile;
import com.adamjurcz.conferenceservice.core.usecases.UseCase;
import lombok.Value;

import java.util.List;

public class GetAllUsersUseCase extends UseCase<GetAllUsersUseCase.Input, GetAllUsersUseCase.Output> {
    private UserProfileRepository userProfileRepository;

    public GetAllUsersUseCase(UserProfileRepository userProfileRepository) {
        this.userProfileRepository = userProfileRepository;
    }

    @Override
    public Output execute(Input input) {
        return new Output(userProfileRepository.getAll());
    }

    @Value
    public static class Input implements UseCase.Input{

    }
    @Value
    public static class Output implements UseCase.Output{
        List<UserProfile>userProfiles;
    }
}
