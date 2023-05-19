package com.adamjurcz.conferenceservice.dataproviders.db.jpa.repositories;

import com.adamjurcz.conferenceservice.core.domain.Identity;
import com.adamjurcz.conferenceservice.core.domain.Lecture;
import com.adamjurcz.conferenceservice.core.usecases.lecture.LectureRepository;
import com.adamjurcz.conferenceservice.dataproviders.db.jpa.entities.LectureData;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class LectureRepositoryImpl implements LectureRepository {
    private JpaLectureRepository jpaLectureRepository;

    public LectureRepositoryImpl(JpaLectureRepository jpaLectureRepository) {
        this.jpaLectureRepository = jpaLectureRepository;
    }

    @Override
    public List<Lecture> getAll() {
        return jpaLectureRepository.findAll().stream().map(LectureData::fromThis).collect(Collectors.toList());
    }

    @Override
    public Optional<Lecture> getById(Identity id) {
        return jpaLectureRepository.findById(id.getValue()).map(LectureData::fromThis);
    }
}
