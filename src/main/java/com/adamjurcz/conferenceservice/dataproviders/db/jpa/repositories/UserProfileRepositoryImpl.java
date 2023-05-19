package com.adamjurcz.conferenceservice.dataproviders.db.jpa.repositories;

import com.adamjurcz.conferenceservice.core.domain.UserProfile;
import com.adamjurcz.conferenceservice.core.usecases.userprofile.UserProfileRepository;
import com.adamjurcz.conferenceservice.dataproviders.db.jpa.entities.UserProfileData;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class UserProfileRepositoryImpl implements UserProfileRepository {
    private JpaUserProfileRepository repository;

    public UserProfileRepositoryImpl(JpaUserProfileRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<UserProfile> getAll() {
        return repository.findAll().stream().map(UserProfileData::fromThis).collect(Collectors.toList());
    }

    @Override
    public List<UserProfile> getAllUsersOnly() {
        return repository.findAll().stream().map(UserProfileData::fromThisWithEmptyLectures).collect(Collectors.toList());
    }

    @Override
    public Optional<UserProfile> getByLogin(String login) {
        return repository.findByLogin(login).map(UserProfileData::fromThis);
    }

    @Override
    public boolean existsByEmail(String email) {
        return repository.existsByEmail(email);
    }

    @Override
    public UserProfile persist(UserProfile userProfile) {
        return repository.save(UserProfileData.from(userProfile)).fromThis();
    }
}
