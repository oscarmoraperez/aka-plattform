package org.oka.aka.orderservice.configuration;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.web.client.RestTemplate;

/**
 * Configuration of Kafka consumers and producers
 */
@EnableKafka
@Configuration
public class OrderServiceConfiguration {
    public static final String TOPIC_PAYMENTS = "payments";

    @Bean
    public RestTemplate restTemplate() {
        // TODO: URL should be passed as a variable p.e. via application.yaml
        return new RestTemplateBuilder().rootUri("http://localhost:8080/").build();
    }
}
