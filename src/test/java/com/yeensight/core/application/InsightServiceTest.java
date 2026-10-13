package com.yeensight.core.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.yeensight.core.domain.model.Insight;
import com.yeensight.core.domain.model.InsightCreated;
import com.yeensight.core.domain.model.InsightId;
import com.yeensight.core.domain.port.InsightEventPublisher;
import com.yeensight.core.domain.port.InsightRepository;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.junit.jupiter.api.Test;

class InsightServiceTest {
    private final Map<InsightId, Insight> store = new HashMap<>();
    private final List<InsightCreated> events = new ArrayList<>();
    private final InsightRepository repository = new InsightRepository() {
        public Insight save(Insight i) { store.put(i.id(), i); return i; }
        public Optional<Insight> findById(InsightId id) { return Optional.ofNullable(store.get(id)); }
    };
    private final InsightEventPublisher publisher = events::add;
    private final InsightService service = new InsightService(repository, publisher);

    @Test
    void createPersistsAndPublishesEvent() {
        Insight created = service.create("t", "c");
        assertThat(store).containsKey(created.id());
        assertThat(events).containsExactly(new InsightCreated(created.id(), "t"));
    }

    @Test
    void getReturnsExistingInsight() {
        Insight created = service.create("t", "c");
        assertThat(service.get(created.id())).isSameAs(created);
    }

    @Test
    void getUnknownThrows() {
        assertThatThrownBy(() -> service.get(InsightId.generate())).isInstanceOf(InsightNotFoundException.class);
    }
}
