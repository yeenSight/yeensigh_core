package com.yeensight.core.infrastructure;

import com.yeensight.core.application.EventService;
import com.yeensight.core.domain.port.EventPublisher;
import com.yeensight.core.domain.port.EventRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {
    @Bean
    EventService eventService(EventRepository repository, EventPublisher publisher) {
        return new EventService(repository, publisher);
    }
}
