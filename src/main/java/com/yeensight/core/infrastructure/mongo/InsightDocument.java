package com.yeensight.core.infrastructure.mongo;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("insights")
public record InsightDocument(@Id String id, String title, String content) {
}
