package com.adamjurcz.conferenceservice.presenters.config;

import com.adamjurcz.conferenceservice.core.usecases.lecture.*;
import com.adamjurcz.conferenceservice.core.usecases.userprofile.*;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Module {
    @Bean
    public CreateLectureReservationUseCase createLectureReservationUseCase(UserProfileRepository userProfileRepository,
                                           LectureRepository lectureRepository, EmailNotificationService emailNotificationService){
        return new CreateLectureReservationUseCase(userProfileRepository, lectureRepository, emailNotificationService);
    }

    @Bean
    public DeleteLectureReservationUseCase deleteLectureReservationUseCase(UserProfileRepository userProfileRepository){
        return new DeleteLectureReservationUseCase(userProfileRepository);
    }

    @Bean
    public EditUserEmailUseCase editUserEmailUseCase(UserProfileRepository userProfileRepository){
        return new EditUserEmailUseCase(userProfileRepository);
    }

    @Bean
    public GetAllUsersUseCase getAllUsersUseCase(UserProfileRepository userProfileRepository){
        return new GetAllUsersUseCase(userProfileRepository);
    }

    @Bean
    public GetUserReservationsUseCase getUserReservationsUseCase(UserProfileRepository userProfileRepository){
        return new GetUserReservationsUseCase(userProfileRepository);
    }

    @Bean
    public GetAllLecturesUseCase getAllLecturesUseCase(LectureRepository lectureRepository){
        return new GetAllLecturesUseCase(lectureRepository);
    }

    @Bean
    public GetLectureInterestUseCase getLectureInterestUseCase(GetAllLecturesUseCase getAllLecturesUseCase){
        return new GetLectureInterestUseCase(getAllLecturesUseCase);
    }

    @Bean
    public GetLecturePathInterestUseCase getLecturePathInterestUseCase(GetAllLecturesUseCase getAllLecturesUseCase){
        return new GetLecturePathInterestUseCase(getAllLecturesUseCase);
    }

    @Bean
    public LogIntoAdminUseCase logIntoAdminUseCase(UserProfileRepository userProfileRepository){
        return new LogIntoAdminUseCase(userProfileRepository);
    }
}
