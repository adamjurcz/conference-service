package com.adamjurcz.conferenceservice.dataproviders.db.jpa.entities;

import com.adamjurcz.conferenceservice.core.domain.Identity;
import com.adamjurcz.conferenceservice.core.domain.Lecture;
import com.adamjurcz.conferenceservice.core.domain.UserProfile;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@Table(name = "user_profile")
@Entity
public class UserProfileData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String login;
    private String email;

    @ManyToMany(mappedBy = "listeners")
    private Set<LectureData> lectures;

    public UserProfile fromThisWithEmptyLectures(){
        return new UserProfile(new Identity(id), login, email, new ArrayList<>());
    }

    public UserProfile fromThis(){
        List<Lecture> lectures = this.getLectures().stream()
                .map(LectureData::fromThisWithEmptyListeners).collect(Collectors.toList());
        return new UserProfile(new Identity(id), login, email, lectures);
    }

    public static UserProfileData fromWithEmptyLectures(UserProfile userProfile){
        return new UserProfileData(userProfile.getId().getValue(), userProfile.getLogin(), userProfile.getEmail(), new HashSet<>());
    }

    public static UserProfileData from(UserProfile userProfile){
        Set<LectureData> lectures = userProfile.getLectures().stream()
                .map(LectureData::fromWithEmptyListeners).collect(Collectors.toSet());
        return new UserProfileData(userProfile.getId().getValue(), userProfile.getLogin(), userProfile.getEmail(), lectures);
    }
}
