package com.adamjurcz.conferenceservice.dataproviders.db.jpa.entities;

import com.adamjurcz.conferenceservice.core.domain.Identity;
import com.adamjurcz.conferenceservice.core.domain.Lecture;
import com.adamjurcz.conferenceservice.core.domain.UserProfile;
import jakarta.persistence.*;
import lombok.*;

import javax.validation.constraints.Size;
import java.util.*;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "user_profile")
@Entity
public class UserProfileData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Size(min = 5, max = 30)
    private String login;
    @Size(max = 60)
    private String email;

    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST},
            fetch = FetchType.LAZY)
    @JoinTable(name = "user_profile_lecture_junction",
            joinColumns = @JoinColumn(name = "user_profile_id"),
            inverseJoinColumns = @JoinColumn(name = "lecture_id")
    )
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



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserProfileData)) return false;
        UserProfileData that = (UserProfileData) o;
        return login.equals(that.login) && email.equals(that.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(login, email);
    }
}
