package com.adamjurcz.conferenceservice.dataproviders.db.jpa.entities;


import com.adamjurcz.conferenceservice.core.domain.Identity;
import com.adamjurcz.conferenceservice.core.domain.Lecture;
import com.adamjurcz.conferenceservice.core.domain.UserProfile;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "lecture")
@Entity
public class LectureData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String main_subject;
    private LocalDateTime start_time;
    private Integer path_number;
    private Integer max_listeners;

    @ManyToMany(mappedBy = "lectures")
    private Set<UserProfileData> listeners;


    public Lecture fromThisWithEmptyListeners(){
        return new Lecture(new Identity(id), main_subject, start_time, path_number, max_listeners, new ArrayList<>());
    }

    public Lecture fromThis(){
        List<UserProfile> listeners = this.getListeners().stream()
                .map(UserProfileData::fromThisWithEmptyLectures).collect(Collectors.toList());
        return new Lecture(new Identity(id), main_subject, start_time, path_number, max_listeners, listeners);
    }

    public static LectureData fromWithEmptyListeners(Lecture lecture){
        return new LectureData(lecture.getId().getValue(), lecture.getMain_subject(), lecture.getStart_time(),
                lecture.getPath_number(), lecture.getMax_listeners(), new HashSet<>());
    }

    public static LectureData from(Lecture lecture){
        Set<UserProfileData> listeners = lecture.getListeners().stream()
                .map(UserProfileData::fromWithEmptyLectures).collect(Collectors.toSet());
        return new LectureData(lecture.getId().getValue(), lecture.getMain_subject(), lecture.getStart_time(),
                lecture.getPath_number(), lecture.getMax_listeners(), listeners);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LectureData)) return false;
        LectureData that = (LectureData) o;
        return main_subject.equals(that.main_subject) &&
                start_time.equals(that.start_time) &&
                path_number.equals(that.path_number) &&
                max_listeners.equals(that.max_listeners);
    }

    @Override
    public int hashCode() {
        return Objects.hash(main_subject, start_time, path_number, max_listeners);
    }
}
