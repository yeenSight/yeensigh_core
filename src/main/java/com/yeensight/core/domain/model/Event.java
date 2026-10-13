package com.yeensight.core.domain.model;

import java.time.Instant;

public class Event {
    private final EventId id;
    private final String title;
    private final String description;
    private final Instant startsAt;
    private final Instant endsAt;
    private final Location location;

    private Event(EventId id, String title, String description, Instant startsAt, Instant endsAt, Location location) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.startsAt = startsAt;
        this.endsAt = endsAt;
        this.location = location;
    }

    public static Event create(String title, String description, Instant startsAt, Instant endsAt, Location location) {
        return restore(EventId.generate(), title, description, startsAt, endsAt, location);
    }

    public static Event restore(EventId id, String title, String description,
                                Instant startsAt, Instant endsAt, Location location) {
        if (id == null) {
            throw new IllegalArgumentException("Event id must not be null");
        }
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("Event title must not be blank");
        }
        if (description == null || description.isBlank()) {
            throw new IllegalArgumentException("Event description must not be blank");
        }
        if (startsAt == null || endsAt == null) {
            throw new IllegalArgumentException("Event start and end are required");
        }
        if (!endsAt.isAfter(startsAt)) {
            throw new IllegalArgumentException("Event end must be after its start");
        }
        if (location == null) {
            throw new IllegalArgumentException("Event location is required");
        }
        return new Event(id, title, description, startsAt, endsAt, location);
    }

    public boolean isOnline() {
        return location instanceof Location.Online;
    }

    public EventId id() { return id; }
    public String title() { return title; }
    public String description() { return description; }
    public Instant startsAt() { return startsAt; }
    public Instant endsAt() { return endsAt; }
    public Location location() { return location; }
}
