package com.yeensight.core.infrastructure.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface SpringDataInsightRepository extends MongoRepository<InsightDocument, String> {
}
