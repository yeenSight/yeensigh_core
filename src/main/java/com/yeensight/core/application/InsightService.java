package com.yeensight.core.application;

import com.yeensight.core.domain.model.Insight;
import com.yeensight.core.domain.model.InsightCreated;
import com.yeensight.core.domain.model.InsightId;
import com.yeensight.core.domain.port.InsightEventPublisher;
import com.yeensight.core.domain.port.InsightRepository;

public class InsightService {
    private final InsightRepository repository;
    private final InsightEventPublisher publisher;

    public InsightService(InsightRepository repository, InsightEventPublisher publisher) {
        this.repository = repository;
        this.publisher = publisher;
    }

    public Insight create(String title, String content) {
        Insight saved = repository.save(Insight.create(title, content));
        publisher.publish(new InsightCreated(saved.id(), saved.title()));
        return saved;
    }

    public Insight get(InsightId id) {
        return repository.findById(id).orElseThrow(() -> new InsightNotFoundException(id));
    }
}
