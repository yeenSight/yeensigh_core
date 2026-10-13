package com.yeensight.core.infrastructure.mongo;

import com.yeensight.core.domain.model.Event;
import com.yeensight.core.domain.model.EventId;
import com.yeensight.core.domain.model.Location;
import com.yeensight.core.domain.port.EventRepository;
import java.net.URI;
import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Component;

@Component
public class MongoEventRepository implements EventRepository {
    private final SpringDataEventRepository delegate;

    public MongoEventRepository(SpringDataEventRepository delegate) {
        this.delegate = delegate;
    }

    @Override
    public Event save(Event event) {
        delegate.save(toDocument(event));
        return event;
    }

    @Override
    public Optional<Event> findById(EventId id) {
        return delegate.findById(id.value().toString()).map(this::toDomain);
    }

    private EventDocument toDocument(Event e) {
        String locationValue = e.location() instanceof Location.Online o
                ? o.url().toString()
                : ((Location.Physical) e.location()).address();
        return new EventDocument(e.id().value().toString(), e.title(), e.description(),
                e.startsAt(), e.endsAt(), e.isOnline(), locationValue);
    }

    private Event toDomain(EventDocument d) {
        Location location = d.online()
                ? new Location.Online(URI.create(d.locationValue()))
                : new Location.Physical(d.locationValue());
        return Event.restore(new EventId(UUID.fromString(d.id())), d.title(), d.description(),
                d.startsAt(), d.endsAt(), location);
    }
}
