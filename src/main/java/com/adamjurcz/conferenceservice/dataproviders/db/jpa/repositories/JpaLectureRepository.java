package com.adamjurcz.conferenceservice.dataproviders.db.jpa.repositories;

import com.adamjurcz.conferenceservice.dataproviders.db.jpa.entities.LectureData;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JpaLectureRepository extends JpaRepository<LectureData, Integer> {
    @EntityGraph(attributePaths = "listeners")
    List<LectureData> findAll();
}
