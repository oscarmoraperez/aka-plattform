package org.oka.aka.orderservice.client;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.contract.stubrunner.StubFinder;
import org.springframework.cloud.contract.stubrunner.spring.AutoConfigureStubRunner;
import org.springframework.cloud.contract.stubrunner.spring.StubRunnerProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.web.client.RestTemplate;

import java.math.BigInteger;

import static org.assertj.core.api.Assertions.assertThat;
import static org.oka.aka.orderservice.client.PaymentClient_WithSCC_Test.PaymentClientTestConfig;

@SpringBootTest(classes = PaymentClient.class)
// TODO: this port (6565) should be dynamic
@AutoConfigureStubRunner(ids = {"org.oka.aka:paymentservice:+:stubs:6565"}, stubsMode = StubRunnerProperties.StubsMode.LOCAL)
@ContextConfiguration(classes = PaymentClientTestConfig.class)
public class PaymentClient_WithSCC_Test {
    @Autowired
    PaymentClient paymentClient;

    @Test
    void shouldReturnPaymentId() {
        // Given

        // When
        int id = paymentClient.createPayment(BigInteger.valueOf(15), "CHF", "CREDIT_CARD", "1234567890123");

        // Then
        assertThat(id).isGreaterThan(0);
    }

    @Configuration
    public static class PaymentClientTestConfig {
        @Bean
        RestTemplate restTemplate() {
            return new RestTemplateBuilder().rootUri("http://localhost:6565").build();
        }
    }
}
