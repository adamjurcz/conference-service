package com.adamjurcz.conferenceservice.core.domain;

import lombok.*;

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

}
