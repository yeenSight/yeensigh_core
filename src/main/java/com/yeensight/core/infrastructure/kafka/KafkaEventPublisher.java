package com.yeensight.core.infrastructure.kafka;

import com.yeensight.core.domain.model.EventCreated;
import com.yeensight.core.domain.port.EventPublisher;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class KafkaEventPublisher implements EventPublisher {
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final String topic;

    public KafkaEventPublisher(KafkaTemplate<String, String> kafkaTemplate,
                               @Value("${yeensight.topics.event-created}") String topic) {
        this.kafkaTemplate = kafkaTemplate;
        this.topic = topic;
    }

    @Override
    public void publish(EventCreated event) {
        String key = event.eventId().value().toString();
        kafkaTemplate.send(topic, key, key);
    }
}
