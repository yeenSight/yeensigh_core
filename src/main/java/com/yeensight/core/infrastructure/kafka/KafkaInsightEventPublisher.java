package com.yeensight.core.infrastructure.kafka;

import com.yeensight.core.domain.model.InsightCreated;
import com.yeensight.core.domain.port.InsightEventPublisher;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class KafkaInsightEventPublisher implements InsightEventPublisher {
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final String topic;

    public KafkaInsightEventPublisher(KafkaTemplate<String, String> kafkaTemplate,
                                      @Value("${yeensight.topics.insight-created}") String topic) {
        this.kafkaTemplate = kafkaTemplate;
        this.topic = topic;
    }

    @Override
    public void publish(InsightCreated event) {
        String key = event.insightId().value().toString();
        kafkaTemplate.send(topic, key, key);
    }
}
