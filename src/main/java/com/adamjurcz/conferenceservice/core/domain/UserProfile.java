package com.adamjurcz.conferenceservice.core.domain;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class UserProfile {
    private Identity id;
    private String login;
    private String email;
    private List<Lecture> lectures;

    public static UserProfile newInstance(String login, String email){
        return new UserProfile(Identity.nothing(), login,
                email, new ArrayList<>());
    }

    public boolean isHourAlreadyReserved(Lecture lecture){
        return lectures.stream()
                .anyMatch(lecture_member ->
                        lecture_member.getStart_time().equals(lecture.getStart_time()));
    }

}
