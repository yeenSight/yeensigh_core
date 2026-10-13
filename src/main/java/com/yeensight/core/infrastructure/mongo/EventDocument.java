package com.yeensight.core.infrastructure.mongo;

import java.time.Instant;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("events")
public record EventDocument(@Id String id, String title, String description, Instant startsAt, Instant endsAt,
                            boolean online, String locationValue) {
}
