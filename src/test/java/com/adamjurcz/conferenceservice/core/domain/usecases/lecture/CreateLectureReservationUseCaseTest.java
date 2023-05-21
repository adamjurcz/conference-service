package com.adamjurcz.conferenceservice.core.domain.usecases.lecture;

import com.adamjurcz.conferenceservice.core.domain.Identity;
import com.adamjurcz.conferenceservice.core.domain.Lecture;
import com.adamjurcz.conferenceservice.core.domain.UserProfile;
import com.adamjurcz.conferenceservice.core.exceptions.AlreadyExistsException;
import com.adamjurcz.conferenceservice.core.exceptions.MaximumListenersInLectureException;
import com.adamjurcz.conferenceservice.core.exceptions.NotFoundException;
import com.adamjurcz.conferenceservice.core.exceptions.UserAlreadyReservedHourException;
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

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
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
    void testSuccessfulReservation(){
        //Given
        Integer lectureIdValue = 1;
        Identity lectureId = new Identity(lectureIdValue);
        Lecture lecture = new Lecture.Builder()
                .setId(lectureId)
                .setMainSubject("Jakis temat")
                .setMaxListeners(5)
                .build();

        Integer userIdValue = 25;
        Identity userId = new Identity(userIdValue);
        String login = "jestemleszek";
        String email = "jestemleszek@wp.pl";
        UserProfile userProfile = new UserProfile.Builder()
                .setId(userId)
                .setLogin(login)
                .setEmail(email)
                .build();

        CreateLectureReservationUseCase.Input input = new CreateLectureReservationUseCase.Input(login, email,
                lectureId);

        //When
        when(userProfileRepository.getByLogin(login)).thenReturn(Optional.of(userProfile));
        when(lectureRepository.getById(lectureId)).thenReturn(Optional.of(lecture));

        // Then
        CreateLectureReservationUseCase.Output output = createLectureReservationUseCase.execute(input);

        verify(userProfileRepository, times(1)).getByLogin(eq(login));
        verify(userProfileRepository, times(1)).persist(any(UserProfile.class));
        verify(emailNotificationService, times(1)).sendNotification(eq(email), eq(lecture));
        verifyNoMoreInteractions(userProfileRepository, lectureRepository, emailNotificationService);

        Assertions.assertEquals(1, userProfile.getLectures().size());
        Assertions.assertEquals(lecture, output.getLecture());
    }

    @Test
    void testLoginAlreadyUsed(){
        //Given
        String login = "jestemleszek";
        String email = "jestemleszek@wp.pl";
        String otherEmail = "innylogin@wp.pl";
        Integer existingUserIdValue = 23;
        Identity existingUserId = new Identity(existingUserIdValue);
        UserProfile existingUser = new UserProfile.Builder()
                .setId(existingUserId)
                .setLogin(login)
                .setEmail(otherEmail)
                .build();

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

    @Test
    void testExecuteMaximumListenersInLecture() {
        //Given
        Integer lectureIdValue = 1;
        Identity lectureId = new Identity(lectureIdValue);
        Lecture lecture = new Lecture.Builder()
                .setId(lectureId)
                .setMainSubject("Jakis temat")
                .setMaxListeners(5)
                .setListeners(new ArrayList<>(Collections.nCopies(5, new UserProfile())))
                .build();


        Integer userIdValue = 25;
        Identity userId = new Identity(userIdValue);
        String login = "jestemleszek";
        String email = "jestemleszek@wp.pl";
        UserProfile userProfile = new UserProfile.Builder()
                .setId(userId)
                .setLogin(login)
                .setEmail(email)
                .build();

        CreateLectureReservationUseCase.Input input = new CreateLectureReservationUseCase.Input(login, email,
                lectureId);

        // When
        when(userProfileRepository.getByLogin(login)).thenReturn(Optional.of(userProfile));
        when(lectureRepository.getById(lectureId)).thenReturn(Optional.of(lecture));

        // Then
        Assertions.assertThrows(MaximumListenersInLectureException.class, () -> createLectureReservationUseCase.execute(input));

        verify(userProfileRepository, times(1)).getByLogin(eq(login));
        verify(userProfileRepository, times(0)).persist(any(UserProfile.class));
        verifyNoInteractions(emailNotificationService);
        verifyNoMoreInteractions(userProfileRepository, lectureRepository);
    }

    @Test
    void testExecuteNonExistingLecture() {
        // Given
        String login = "jestemleszek";
        String email = "jestemleszek@wp.pl";
        Integer nonExistingLectureIdValue = 2;
        Identity nonExistingLectureId = new Identity(nonExistingLectureIdValue);

        CreateLectureReservationUseCase.Input input = new CreateLectureReservationUseCase.Input(login, email,
                nonExistingLectureId);

        // When
        when(userProfileRepository.getByLogin(login)).thenReturn(Optional.empty());
        when(lectureRepository.getById(nonExistingLectureId)).thenReturn(Optional.empty());

        // Then
        Assertions.assertThrows(NotFoundException.class, () -> createLectureReservationUseCase.execute(input));

        verify(userProfileRepository, times(1)).getByLogin(eq(login));
        verify(userProfileRepository, times(0)).persist(any(UserProfile.class));
        verifyNoInteractions(emailNotificationService);
        verifyNoMoreInteractions(userProfileRepository, lectureRepository);
    }

    @Test
    void testExecuteUserAlreadyReservedHour() {
        // Given
        Integer lectureIdValue = 1;
        Identity lectureId = new Identity(lectureIdValue);
        Lecture lecture = new Lecture.Builder()
                .setId(lectureId)
                .setMainSubject("Jakis temat")
                .setMaxListeners(5)
                .setStartTime(LocalDateTime.now())
                .build();

        Integer userIdValue = 25;
        Identity userId = new Identity(userIdValue);
        String login = "jestemleszek";
        String email = "jestemleszek@wp.pl";
        UserProfile userProfile = new UserProfile.Builder()
                .setId(userId)
                .setLogin(login)
                .setEmail(email)
                .addLecture(lecture)
                .build();

        CreateLectureReservationUseCase.Input input = new CreateLectureReservationUseCase.Input(login, email,
                lectureId);

        // When
        when(userProfileRepository.getByLogin(login)).thenReturn(Optional.of(userProfile));
        when(lectureRepository.getById(lectureId)).thenReturn(Optional.of(lecture));

        // Then
        Assertions.assertThrows(UserAlreadyReservedHourException.class, () -> createLectureReservationUseCase.execute(input));

        verify(userProfileRepository, times(1)).getByLogin(eq(login));
        verify(userProfileRepository, times(0)).persist(any(UserProfile.class));
        verifyNoInteractions(emailNotificationService);
        verifyNoMoreInteractions(userProfileRepository, lectureRepository);
    }



}
