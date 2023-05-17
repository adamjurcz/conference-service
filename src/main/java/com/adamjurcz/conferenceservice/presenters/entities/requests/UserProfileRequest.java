package com.adamjurcz.conferenceservice.presenters.entities.requests;

import lombok.Value;

@Value
public class UserProfileRequest {
    String login;
    String email;
}
