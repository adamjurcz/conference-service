package com.adamjurcz.conferenceservice.core.usecases.userprofile;

import com.adamjurcz.conferenceservice.core.domain.Identity;
import com.adamjurcz.conferenceservice.core.domain.Lecture;
import com.adamjurcz.conferenceservice.core.domain.UserProfile;

import java.util.List;
import java.util.Optional;

public interface UserProfileRepository {

    List<UserProfile> getAllUsersOnly();

    Optional<UserProfile> getByLogin(String string);

    UserProfile persist(UserProfile userProfile);

    boolean existsByEmail(String email);

}
