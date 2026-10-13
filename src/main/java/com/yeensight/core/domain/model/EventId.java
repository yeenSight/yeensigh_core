package com.yeensight.core.domain.model;

import java.util.UUID;

public record EventId(UUID value) {
    public EventId {
        if (value == null) {
            throw new IllegalArgumentException("Event id must not be null");
        }
    }

    public static EventId generate() {
        return new EventId(UUID.randomUUID());
    }
}
