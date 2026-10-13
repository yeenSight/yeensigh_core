package com.yeensight.core.infrastructure;

import com.yeensight.core.application.InsightService;
import com.yeensight.core.domain.port.InsightEventPublisher;
import com.yeensight.core.domain.port.InsightRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {
    @Bean
    InsightService insightService(InsightRepository repository, InsightEventPublisher publisher) {
        return new InsightService(repository, publisher);
    }
}
