package com.adamjurcz.conferenceservice.core.domain;

import lombok.Value;

@Value
public class Identity {
    Long value;

    public static Identity nothing(){
        return new Identity(Long.MIN_VALUE);
    }
}
