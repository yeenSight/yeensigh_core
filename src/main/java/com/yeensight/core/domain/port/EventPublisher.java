package com.yeensight.core.domain.port;

import com.yeensight.core.domain.model.EventCreated;

public interface EventPublisher {
    void publish(EventCreated event);
}
