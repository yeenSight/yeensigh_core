package com.yeensight.core.application;

import com.yeensight.core.domain.model.EventId;

public class EventNotFoundException extends RuntimeException {
    public EventNotFoundException(EventId id) {
        super("Event not found: " + id.value());
    }
}
