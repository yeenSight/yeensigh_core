package com.yeensight.core.infrastructure.mongo;

import com.yeensight.core.domain.model.Insight;
import com.yeensight.core.domain.model.InsightId;
import com.yeensight.core.domain.port.InsightRepository;
import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Component;

@Component
public class MongoInsightRepository implements InsightRepository {
    private final SpringDataInsightRepository delegate;

    public MongoInsightRepository(SpringDataInsightRepository delegate) {
        this.delegate = delegate;
    }

    @Override
    public Insight save(Insight insight) {
        delegate.save(new InsightDocument(insight.id().value().toString(), insight.title(), insight.content()));
        return insight;
    }

    @Override
    public Optional<Insight> findById(InsightId id) {
        return delegate.findById(id.value().toString())
                .map(d -> Insight.restore(new InsightId(UUID.fromString(d.id())), d.title(), d.content()));
    }
}
