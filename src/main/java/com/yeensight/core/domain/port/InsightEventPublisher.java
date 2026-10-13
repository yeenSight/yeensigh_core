package com.yeensight.core.domain.port;

import com.yeensight.core.domain.model.InsightCreated;

public interface InsightEventPublisher {
    void publish(InsightCreated event);
}
