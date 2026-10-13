package com.yeensight.core.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.yeensight.core.domain.model.Event;
import com.yeensight.core.domain.model.Location;
import java.net.URI;
import java.time.Instant;
import org.junit.jupiter.api.Test;

class EventTest {
    private static final Instant START = Instant.parse("2026-11-01T18:00:00Z");
    private static final Instant END = Instant.parse("2026-11-01T20:00:00Z");
    private static final Location PLACE = new Location.Physical("1 rue de Paris");

    @Test
    void createsInPersonEvent() {
        Event e = Event.create("Meetup", "desc", START, END, PLACE);
        assertThat(e.id()).isNotNull();
        assertThat(e.isOnline()).isFalse();
    }

    @Test
    void createsOnlineEvent() {
        Event e = Event.create("Webinar", "desc", START, END, new Location.Online(URI.create("https://meet.example.com/x")));
        assertThat(e.isOnline()).isTrue();
    }

    @Test
    void rejectsBlankTitle() {
        assertThatThrownBy(() -> Event.create(" ", "desc", START, END, PLACE)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void rejectsBlankDescription() {
        assertThatThrownBy(() -> Event.create("t", "", START, END, PLACE)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void rejectsEndNotAfterStart() {
        assertThatThrownBy(() -> Event.create("t", "d", END, START, PLACE)).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> Event.create("t", "d", START, START, PLACE)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void rejectsMissingLocation() {
        assertThatThrownBy(() -> Event.create("t", "d", START, END, null)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void rejectsBlankAddress() {
        assertThatThrownBy(() -> new Location.Physical(" ")).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void rejectsNonHttpOnlineUrl() {
        assertThatThrownBy(() -> new Location.Online(URI.create("ftp://x"))).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> new Location.Online(URI.create("nothing"))).isInstanceOf(IllegalArgumentException.class);
    }
}
