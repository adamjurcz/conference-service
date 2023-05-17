package com.adamjurcz.conferenceservice.dataproviders.db.jpa.repositories;

import com.adamjurcz.conferenceservice.dataproviders.db.jpa.entities.LectureData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaLectureRepository extends JpaRepository<LectureData, Integer> {
}
