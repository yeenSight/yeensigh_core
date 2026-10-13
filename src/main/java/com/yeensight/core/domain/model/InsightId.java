package com.yeensight.core.domain.model;

import java.util.UUID;

public record InsightId(UUID value) {
    public InsightId {
        if (value == null) {
            throw new IllegalArgumentException("Insight id must not be null");
        }
    }

    public static InsightId generate() {
        return new InsightId(UUID.randomUUID());
    }
}
