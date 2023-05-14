package com.adamjurcz.conferenceservice.core.usecases.lecture;

import com.adamjurcz.conferenceservice.core.domain.Identity;
import com.adamjurcz.conferenceservice.core.domain.Lecture;

import java.util.List;
import java.util.Optional;

public interface LectureRepository {
    List<Lecture> getAll();

    Optional<Lecture> getById(Identity id);
}
