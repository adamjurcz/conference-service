package com.adamjurcz.conferenceservice.core.domain;

import lombok.*;

import java.time.LocalDateTime;
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
        return !(listeners.size() <= max_listeners);
    }
}
