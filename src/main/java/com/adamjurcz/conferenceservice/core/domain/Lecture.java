package com.adamjurcz.conferenceservice.core.domain;

import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class Lecture {
    private Identity id;
    private String main_subject;
    private LocalDateTime start_time;
    private Integer path_number;
    private Integer max_listeners;

    private List<UserProfile> listeners;

    public boolean hasMaximumListeners(){
        return !(listeners.size() < max_listeners);
    }

    public static class Builder {
        private Identity id;
        private String main_subject;
        private LocalDateTime start_time;
        private Integer path_number;
        private Integer max_listeners;
        private List<UserProfile> listeners;

        public Builder() {
            listeners = new ArrayList<>();
        }

        public Builder setId(Identity id) {
            this.id = id;
            return this;
        }

        public Builder setMainSubject(String main_subject) {
            this.main_subject = main_subject;
            return this;
        }

        public Builder setStartTime(LocalDateTime start_time) {
            this.start_time = start_time;
            return this;
        }

        public Builder setPathNumber(Integer path_number) {
            this.path_number = path_number;
            return this;
        }

        public Builder setMaxListeners(Integer max_listeners) {
            this.max_listeners = max_listeners;
            return this;
        }

        public Builder addListener(UserProfile listener) {
            this.listeners.add(listener);
            return this;
        }

        public Builder setListeners(List<UserProfile> userProfiles){
            this.listeners = userProfiles;
            return this;
        }

        public Lecture build() {
            return new Lecture(id, main_subject, start_time, path_number, max_listeners, listeners);
        }
    }
}
