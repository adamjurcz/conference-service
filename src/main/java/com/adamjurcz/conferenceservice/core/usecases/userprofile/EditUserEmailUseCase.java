package com.adamjurcz.conferenceservice.core.usecases.userprofile;

import com.adamjurcz.conferenceservice.core.domain.UserProfile;
import com.adamjurcz.conferenceservice.core.exceptions.AlreadyExistsException;
import com.adamjurcz.conferenceservice.core.exceptions.InvalidDataException;
import com.adamjurcz.conferenceservice.core.exceptions.NotFoundException;
import com.adamjurcz.conferenceservice.core.usecases.UseCase;
import lombok.Value;

public class EditUserEmailUseCase extends UseCase<EditUserEmailUseCase.Input, EditUserEmailUseCase.Output> {
    private UserProfileRepository userProfileRepository;

    public EditUserEmailUseCase(UserProfileRepository userProfileRepository) {
        this.userProfileRepository = userProfileRepository;
    }

    @Override
    public Output execute(Input input) {
        UserProfile userProfile = userProfileRepository
                .getByLogin(input.getLogin())
                .orElseThrow(()->new NotFoundException("Login: %s nie istnieje!", input.getLogin()));

        if(userProfile.getEmail().equals(input.getNewEmail())){
            throw new AlreadyExistsException("Email: %s juz istnieje!", input.getNewEmail());
        }
        if(!UserProfile.isEmailValid(input.getNewEmail())){
            throw new InvalidDataException("Email: %s ma nieprawidlowy format!", input.getNewEmail());
        }

        userProfile.setEmail(input.getNewEmail());
        userProfileRepository.persist(userProfile);
        return new Output(userProfile);
    }

    @Value
    public static class Input implements UseCase.Input{
        String login;
        String newEmail;
    }

    @Value
    public static class Output implements UseCase.Output{
        UserProfile userProfile;
    }
}
