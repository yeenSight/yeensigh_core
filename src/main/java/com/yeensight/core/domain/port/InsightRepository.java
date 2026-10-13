package com.yeensight.core.domain.port;

import com.yeensight.core.domain.model.Insight;
import com.yeensight.core.domain.model.InsightId;
import java.util.Optional;

public interface InsightRepository {
    Insight save(Insight insight);

    Optional<Insight> findById(InsightId id);
}
