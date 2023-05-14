package com.adamjurcz.conferenceservice.core.usecases.userprofile;

import com.adamjurcz.conferenceservice.core.domain.Identity;
import com.adamjurcz.conferenceservice.core.domain.UserProfile;

import java.util.Optional;

public interface UserProfileRepository {
    Optional<UserProfile> getByLogin(String string);

}
