package com.adamjurcz.conferenceservice.presenters.entities.responses;

import lombok.Value;

import java.io.Serializable;

@Value
public class UserProfileIsAdminResponse implements Serializable {
    boolean isAdmin;
}
