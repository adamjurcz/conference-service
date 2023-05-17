package com.adamjurcz.conferenceservice.core.domain;

import lombok.Value;

@Value
public class Identity {
    Integer value;

    public static Identity nothing(){
        return new Identity(Integer.MIN_VALUE);
    }
}
