package com.adamjurcz.conferenceservice.presenters.entities.requests;

import lombok.Value;

@Value
public class UserProfileResponse {
    String login;
    String email;
}
