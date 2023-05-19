package com.adamjurcz.conferenceservice.dataproviders.db.jpa.repositories;

import com.adamjurcz.conferenceservice.dataproviders.db.jpa.entities.UserProfileData;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JpaUserProfileRepository extends JpaRepository<UserProfileData, Integer> {

    @EntityGraph(attributePaths = "lectures")
    Optional<UserProfileData> findByLogin(String login);

    boolean existsByEmail(String email);

}
