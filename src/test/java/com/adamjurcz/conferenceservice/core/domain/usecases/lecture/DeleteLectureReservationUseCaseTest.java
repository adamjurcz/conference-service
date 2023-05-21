package com.adamjurcz.conferenceservice.core.domain.usecases.lecture;

import com.adamjurcz.conferenceservice.core.domain.Identity;
import com.adamjurcz.conferenceservice.core.domain.Lecture;
import com.adamjurcz.conferenceservice.core.domain.UserProfile;
import com.adamjurcz.conferenceservice.core.exceptions.NotFoundException;
import com.adamjurcz.conferenceservice.core.usecases.lecture.DeleteLectureReservationUseCase;
import com.adamjurcz.conferenceservice.core.usecases.userprofile.UserProfileRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DeleteLectureReservationUseCaseTest {

    @Mock
    UserProfileRepository userProfileRepository;

    @InjectMocks
    DeleteLectureReservationUseCase deleteLectureReservationUseCase;

    @BeforeEach
    void setUp(){
    }

    @Test
    void testSuccessfulReservationDeletion(){
        //Given
        Integer lectureIdValue = 1;
        Identity lectureId = new Identity(lectureIdValue);
        Lecture lecture = new Lecture.Builder()
                .setId(lectureId)
                .setMaxListeners(5)
                .build();

        Integer userIdValue = 25;
        Identity userId = new Identity(userIdValue);
        String login = "maciek";
        String email = "maciek@wp.pl";
        UserProfile userProfile = new UserProfile.Builder()
                .setId(userId)
                .setLogin(login)
                .setEmail(email)
                .addLecture(lecture)
                .build();

        DeleteLectureReservationUseCase.Input input = new DeleteLectureReservationUseCase.Input(login, lectureId);
        //When
        when(userProfileRepository.getByLogin(login)).thenReturn(Optional.of(userProfile));

        //Then
        DeleteLectureReservationUseCase.Output output = deleteLectureReservationUseCase.execute(input);

        verify(userProfileRepository, times(1)).getByLogin(eq(login));
        verify(userProfileRepository, times(1)).persist(eq(userProfile));

        Assertions.assertEquals(0, userProfile.getLectures().size());
    }

    @Test
    void testDeleteReservationWithInvalidLogin() {
        // Given
        String login = "zlylogin";
        Integer lectureIdValue = 1;
        Identity lectureId = new Identity(lectureIdValue);

        DeleteLectureReservationUseCase.Input input = new DeleteLectureReservationUseCase.Input(login, lectureId);

        // When
        when(userProfileRepository.getByLogin(login)).thenReturn(Optional.empty());

        // Then
        Assertions.assertThrows(NotFoundException.class, () -> deleteLectureReservationUseCase.execute(input));

        verify(userProfileRepository, times(1)).getByLogin(eq(login));
        verify(userProfileRepository, never()).persist(any());
    }

    @Test
    void testDeleteReservationWithNonExistingLecture() {
        // Given
        Integer userIdValue = 25;
        Identity userId = new Identity(userIdValue);
        String login = "maciek";
        String email = "maciek@wp.pl";
        UserProfile userProfile = new UserProfile.Builder()
                .setId(userId)
                .setLogin(login)
                .setEmail(email)
                .build();

        Integer nonExistingLectureIdValue = 2;
        Identity nonExistingLectureId = new Identity(nonExistingLectureIdValue);

        DeleteLectureReservationUseCase.Input input = new DeleteLectureReservationUseCase.Input(login, nonExistingLectureId);

        // When
        when(userProfileRepository.getByLogin(login)).thenReturn(Optional.of(userProfile));

        // Then
        Assertions.assertThrows(NotFoundException.class, () -> deleteLectureReservationUseCase.execute(input));

        verify(userProfileRepository, times(1)).getByLogin(eq(login));
        verify(userProfileRepository, never()).persist(any());
    }
}
