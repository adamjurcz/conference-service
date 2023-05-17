package com.adamjurcz.conferenceservice.dataproviders.db.jpa.repositories;

import com.adamjurcz.conferenceservice.dataproviders.db.jpa.entities.UserProfileData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaUserProfileRepository extends JpaRepository<UserProfileData, Integer> {

}
