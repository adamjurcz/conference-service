package com.adamjurcz.conferenceservice.core.domain;

import lombok.*;

import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class Lecture {
    private Identity id;
    private String main_subject;
    private Date start_time;
    private Integer path_num;
    private Integer max_listeners;
    private List<UserProfile> listeners;

    public boolean hasMaximumListeners(){
        return !(listeners.size() <= 5);
    }
}
