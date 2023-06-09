package com.adamjurcz.conferenceservice.core.domain;

import lombok.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
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

    public static class Builder {
        private Identity id;
        private String login;
        private String email;
        private List<Lecture> lectures;

        public Builder() {
            lectures = new ArrayList<>();
        }

        public Builder setId(Identity id) {
            this.id = id;
            return this;
        }

        public Builder setLogin(String login) {
            this.login = login;
            return this;
        }

        public Builder setEmail(String email) {
            this.email = email;
            return this;
        }

        public Builder addLecture(Lecture lecture) {
            this.lectures.add(lecture);
            return this;
        }

        public UserProfile build() {
            return new UserProfile(id, login, email, lectures);
        }
    }
}
