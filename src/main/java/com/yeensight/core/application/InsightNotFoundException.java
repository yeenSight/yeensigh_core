package com.yeensight.core.application;

import com.yeensight.core.domain.model.InsightId;

public class InsightNotFoundException extends RuntimeException {
    public InsightNotFoundException(InsightId id) {
        super("Insight not found: " + id.value());
    }
}
