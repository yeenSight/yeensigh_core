package com.yeensight.core.infrastructure.rest;

import com.yeensight.core.application.EventNotFoundException;
import com.yeensight.core.application.EventService;
import com.yeensight.core.domain.model.Event;
import com.yeensight.core.domain.model.EventId;
import com.yeensight.core.domain.model.Location;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.time.Instant;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/events")
public class EventController {
    public enum EventType { IN_PERSON, ONLINE }

    public record CreateEventRequest(@NotBlank String title, @NotBlank String description,
                                     @NotNull Instant startsAt, @NotNull Instant endsAt,
                                     @NotNull EventType type, @NotBlank String location) {
        Location toLocation() {
            return type == EventType.ONLINE ? new Location.Online(URI.create(location)) : new Location.Physical(location);
        }
    }

    public record EventResponse(UUID id, String title, String description, Instant startsAt, Instant endsAt,
                                EventType type, String location) {
        static EventResponse from(Event e) {
            boolean online = e.location() instanceof Location.Online;
            String location = online
                    ? ((Location.Online) e.location()).url().toString()
                    : ((Location.Physical) e.location()).address();
            return new EventResponse(e.id().value(), e.title(), e.description(), e.startsAt(), e.endsAt(),
                    online ? EventType.ONLINE : EventType.IN_PERSON, location);
        }
    }

    private final EventService service;

    public EventController(EventService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EventResponse create(@Valid @RequestBody CreateEventRequest r) {
        return EventResponse.from(service.create(r.title(), r.description(), r.startsAt(), r.endsAt(), r.toLocation()));
    }

    @GetMapping("/{id}")
    public EventResponse get(@PathVariable UUID id) {
        return EventResponse.from(service.get(new EventId(id)));
    }

    @ExceptionHandler(EventNotFoundException.class)
    ResponseEntity<Void> notFound() {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(IllegalArgumentException.class)
    ResponseEntity<String> badRequest(IllegalArgumentException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }
}
