package com.adamjurcz.conferenceservice.core.domain.usecases.lecture;

import com.adamjurcz.conferenceservice.core.domain.Identity;
import com.adamjurcz.conferenceservice.core.domain.Lecture;
import com.adamjurcz.conferenceservice.core.domain.UserProfile;
import com.adamjurcz.conferenceservice.core.exceptions.AlreadyExistsException;
import com.adamjurcz.conferenceservice.core.usecases.lecture.CreateLectureReservationUseCase;
import com.adamjurcz.conferenceservice.core.usecases.lecture.EmailNotificationService;
import com.adamjurcz.conferenceservice.core.usecases.lecture.LectureRepository;
import com.adamjurcz.conferenceservice.core.usecases.userprofile.UserProfileRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CreateLectureReservationUseCaseTest {

    @Mock
    UserProfileRepository userProfileRepository;

    @Mock
    LectureRepository lectureRepository;

    @Mock
    EmailNotificationService emailNotificationService;

    @InjectMocks
    CreateLectureReservationUseCase createLectureReservationUseCase;


    @BeforeEach
    void setUp(){
    }

    @Test
    void testExecuteSuccessfulReservation(){
        //Given
        String login = "jestemleszek";
        String email = "jestemleszek@wp.pl";
        Integer lectureIdValue = 1;
        Identity lectureId = new Identity(lectureIdValue);
        UserProfile userProfile = UserProfile.newInstance(login, email);

        Lecture lecture = new Lecture();
        lecture.setId(lectureId);
        lecture.setListeners(new ArrayList<>());
        lecture.setMax_listeners(5);

        CreateLectureReservationUseCase.Input input = new CreateLectureReservationUseCase.Input(login, email,
                lectureId);

        //When
        when(userProfileRepository.getByLogin(login)).thenReturn(Optional.of(userProfile));
        when(lectureRepository.getById(lectureId)).thenReturn(Optional.of(lecture));

        CreateLectureReservationUseCase.Output output = createLectureReservationUseCase.execute(input);

        // Then
        verify(userProfileRepository, times(1)).getByLogin(eq(login));
        verify(userProfileRepository, times(1)).persist(any(UserProfile.class));
        verify(emailNotificationService, times(1)).sendNotification(eq(email), eq(lecture));
        verifyNoMoreInteractions(userProfileRepository, lectureRepository, emailNotificationService);

        Assertions.assertEquals(1, userProfile.getLectures().size());
        Assertions.assertEquals(lecture, output.getLecture());
    }

    @Test
    void testExecuteLoginAlreadyUsed(){
        //Given
        String login = "jestemleszek";
        String email = "jestemleszek@wp.pl";
        String otherEmail = "innylogin@wp.pl";
        UserProfile existingUser = UserProfile.newInstance(login, otherEmail);

        Integer lectureIdValue = 3;
        Identity lectureId = new Identity(lectureIdValue);

        CreateLectureReservationUseCase.Input input = new CreateLectureReservationUseCase.Input(login, email,
                lectureId);

        //When
        when(userProfileRepository.getByLogin(login)).thenReturn(Optional.of(existingUser));

        //Then
        Assertions.assertThrows(AlreadyExistsException.class, ()->{
            CreateLectureReservationUseCase.Output output = createLectureReservationUseCase.execute(input);
        });

        verify(userProfileRepository, times(1)).getByLogin(eq(login));
        verify(userProfileRepository, times(0)).persist(any(UserProfile.class));
        verifyNoInteractions(lectureRepository);
        verifyNoInteractions(emailNotificationService);
    }


}
