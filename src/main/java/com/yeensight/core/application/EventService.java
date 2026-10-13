package com.yeensight.core.application;

import com.yeensight.core.domain.model.Event;
import com.yeensight.core.domain.model.EventCreated;
import com.yeensight.core.domain.model.EventId;
import com.yeensight.core.domain.model.Location;
import com.yeensight.core.domain.port.EventPublisher;
import com.yeensight.core.domain.port.EventRepository;
import java.time.Instant;

public class EventService {
    private final EventRepository repository;
    private final EventPublisher publisher;

    public EventService(EventRepository repository, EventPublisher publisher) {
        this.repository = repository;
        this.publisher = publisher;
    }

    public Event create(String title, String description, Instant startsAt, Instant endsAt, Location location) {
        Event saved = repository.save(Event.create(title, description, startsAt, endsAt, location));
        publisher.publish(new EventCreated(saved.id(), saved.title()));
        return saved;
    }

    public Event get(EventId id) {
        return repository.findById(id).orElseThrow(() -> new EventNotFoundException(id));
    }
}
