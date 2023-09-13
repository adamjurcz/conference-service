package com.adamjurcz.conferenceservice.core.usecases.userprofile;

import com.adamjurcz.conferenceservice.core.domain.UserProfile;
import com.adamjurcz.conferenceservice.core.usecases.UseCase;
import lombok.Value;

public class LogIntoAdminUseCase extends UseCase<LogIntoAdminUseCase.Input, LogIntoAdminUseCase.Output>{
    private final UserProfileRepository userProfileRepository;

    public LogIntoAdminUseCase(UserProfileRepository userProfileRepository) {
        this.userProfileRepository = userProfileRepository;
    }

    @Override
    public Output execute(Input input) {
        UserProfile userProfile = userProfileRepository.getByLogin(input.getLogin())
                .orElse(null);

        if(userProfile==null){
            return new Output(false);
        }

        if(userProfile.getIsAdmin() && userProfile.getPassword().equals(input.getPassword())){
            return new Output(true);
        }
        else{
            return new Output(false);
        }
    }

    @Value
    public static class Input implements UseCase.Input{
        String login;
        String password;
    }

    @Value
    public static class Output implements UseCase.Output{
        boolean canLogIntoAdmin;
    }
}
