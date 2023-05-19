package com.adamjurcz.conferenceservice.core.usecases.lecture;

import com.adamjurcz.conferenceservice.core.domain.Lecture;

public interface EmailNotificationService {
    void sendNotification(String email, Lecture lecture);
}
