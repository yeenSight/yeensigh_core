package com.yeensight.core.domain.port;

import com.yeensight.core.domain.model.Event;
import com.yeensight.core.domain.model.EventId;
import java.util.Optional;

public interface EventRepository {
    Event save(Event event);

    Optional<Event> findById(EventId id);
}
