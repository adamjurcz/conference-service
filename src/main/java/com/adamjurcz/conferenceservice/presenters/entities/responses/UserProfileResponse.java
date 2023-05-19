package com.adamjurcz.conferenceservice.presenters.entities.responses;

import lombok.Value;

import java.io.Serializable;

@Value
public class UserProfileResponse implements Serializable {
    Integer id;
    String login;
    String email;
}
