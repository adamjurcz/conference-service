package com.adamjurcz.conferenceservice.core.domain;

import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

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

    public static boolean isEmailValid(String email){
        return ((Pattern.compile("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")
                .matcher(email)
                .matches()) &&
                email.length()<60
                );
    }

    public boolean isHourAlreadyReserved(Lecture lecture){
        return lectures.stream()
                .anyMatch(lecture_member ->
                        lecture_member.getStart_time().equals(lecture.getStart_time()));
    }

}
