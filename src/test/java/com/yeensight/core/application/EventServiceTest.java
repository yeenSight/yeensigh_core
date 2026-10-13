package com.yeensight.core.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.yeensight.core.domain.model.Event;
import com.yeensight.core.domain.model.EventCreated;
import com.yeensight.core.domain.model.EventId;
import com.yeensight.core.domain.model.Location;
import com.yeensight.core.domain.port.EventPublisher;
import com.yeensight.core.domain.port.EventRepository;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.junit.jupiter.api.Test;

class EventServiceTest {
    private final Map<EventId, Event> store = new HashMap<>();
    private final List<EventCreated> events = new ArrayList<>();
    private final EventRepository repository = new EventRepository() {
        public Event save(Event e) { store.put(e.id(), e); return e; }
        public Optional<Event> findById(EventId id) { return Optional.ofNullable(store.get(id)); }
    };
    private final EventPublisher publisher = events::add;
    private final EventService service = new EventService(repository, publisher);

    private Event createSample() {
        return service.create("t", "d", Instant.parse("2026-11-01T18:00:00Z"),
                Instant.parse("2026-11-01T19:00:00Z"), new Location.Physical("Paris"));
    }

    @Test
    void createPersistsAndPublishesEvent() {
        Event created = createSample();
        assertThat(store).containsKey(created.id());
        assertThat(events).containsExactly(new EventCreated(created.id(), "t"));
    }

    @Test
    void invalidEventIsNeitherSavedNorPublished() {
        Instant t = Instant.parse("2026-11-01T18:00:00Z");
        assertThatThrownBy(() -> service.create("t", "d", t, t, new Location.Physical("Paris")))
                .isInstanceOf(IllegalArgumentException.class);
        assertThat(store).isEmpty();
        assertThat(events).isEmpty();
    }

    @Test
    void getReturnsExistingEvent() {
        Event created = createSample();
        assertThat(service.get(created.id())).isSameAs(created);
    }

    @Test
    void getUnknownThrows() {
        assertThatThrownBy(() -> service.get(EventId.generate())).isInstanceOf(EventNotFoundException.class);
    }
}
