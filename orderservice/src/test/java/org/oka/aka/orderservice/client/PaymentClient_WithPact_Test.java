package org.oka.aka.orderservice.client;

import au.com.dius.pact.consumer.MockServer;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.consumer.junit5.PactConsumerTest;
import au.com.dius.pact.consumer.junit5.PactTestFor;
import au.com.dius.pact.core.model.V4Pact;
import au.com.dius.pact.core.model.annotations.Pact;
import org.json.JSONObject;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.web.client.RestTemplate;

import java.math.BigInteger;
import java.util.Map;

import static au.com.dius.pact.consumer.dsl.LambdaDsl.newJsonBody;
import static org.assertj.core.api.Assertions.assertThat;
import static org.oka.aka.orderservice.client.PaymentClient_WithPact_Test.PaymentClientTestConfig;

@PactConsumerTest
@ContextConfiguration(classes = PaymentClientTestConfig.class)
@Tag("PactTest")
public class PaymentClient_WithPact_Test {

    @Pact(consumer = "order-service", provider = "payment-service")
    V4Pact createPayment(PactDslWithProvider builder) {
        return builder.given("There are no payments in the system")
                .uponReceiving("I create a new payment")
                .method("POST")
                .path("/api/paymentservice/payments")
                .headers(Map.of("Content-Type", "application/json"))
                .body(new JSONObject(Map.of("amount", 15, "currency", "CHF", "type", "CREDIT_CARD", "cardNumber", "1234567890123")))
                .willRespondWith()
                .status(200)
                .headers(Map.of("Content-Type", "application/json"))
                .body(newJsonBody(object -> {
                            object.integerType("id", 1);
                            object.integerType("amount", 15);
                            object.stringType("currency", "CHF");
                            object.stringType("type", "CREDIT_CARD");
                            object.stringType("cardNumber", "1234567890123");
                            object.stringType("status", "CREATED");
                        }
                ).build())
                .toPact(V4Pact.class);
    }


    @Test
    @PactTestFor(pactMethod = "createPayment")
    void getAllProducts_whenProductsExist(MockServer mockServer) {
        // Given
        RestTemplate restTemplate = new RestTemplateBuilder().rootUri(mockServer.getUrl()).build();
        PaymentClient paymentClient = new PaymentClient(restTemplate);

        // When
        PaymentResponse payment = paymentClient.createPayment(BigInteger.valueOf(15), "CHF", "CREDIT_CARD", "1234567890123");

        // Then
        assertThat(payment.getId()).isEqualTo(1);
        assertThat(payment.getStatus()).isEqualTo("CREATED");
    }

    @Configuration
    public static class PaymentClientTestConfig {
    }
}
