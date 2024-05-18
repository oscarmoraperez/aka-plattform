package org.oka.aka.paymentservice.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;

/**
 * Configuration of Kafka consumers and producers
 */
@EnableKafka
@Configuration
public class PaymentServiceConfiguration {
    public static final String TOPIC_PAYMENTS = "payments";
}
