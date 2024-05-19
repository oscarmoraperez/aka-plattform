package org.oka.aka.orderservice.listener;

import org.junit.jupiter.api.Test;
import org.oka.aka.orderservice.OrderServiceApplication;
import org.oka.aka.orderservice.listener.PaymentMessageListener_WithSCC_Test.TestConfig;
import org.oka.aka.orderservice.model.OrderEntity;
import org.oka.aka.orderservice.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.cloud.contract.stubrunner.StubTrigger;
import org.springframework.cloud.contract.stubrunner.spring.AutoConfigureStubRunner;
import org.springframework.cloud.contract.stubrunner.spring.StubRunnerProperties;
import org.springframework.cloud.contract.verifier.converter.YamlContract;
import org.springframework.cloud.contract.verifier.messaging.MessageVerifierSender;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.lang.Nullable;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.util.Map;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.kafka.support.KafkaHeaders.TOPIC;
import static org.springframework.messaging.support.MessageBuilder.createMessage;

@SpringBootTest(webEnvironment = RANDOM_PORT, classes = {OrderServiceApplication.class, TestConfig.class})
@AutoConfigureStubRunner(ids = {"org.oka.aka:paymentservice:+:stubs:6565"}, stubsMode = StubRunnerProperties.StubsMode.LOCAL)
@Testcontainers
public class PaymentMessageListener_WithSCC_Test {
    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> DB = new PostgreSQLContainer<>("postgres:16-alpine").withReuse(true);
    @Container
    @ServiceConnection
    static KafkaContainer KAFKA = new KafkaContainer(DockerImageName.parse("confluentinc/cp-kafka:latest")).withReuse(true);

    @Autowired
    OrderRepository orderRepository;
    @Autowired
    StubTrigger stubTrigger;

    @DynamicPropertySource
    static void kafkaProperties(DynamicPropertyRegistry registry) {
        KAFKA.start();
        registry.add("spring.kafka.bootstrap-servers", () -> KAFKA.getBootstrapServers());
    }

    @Test
    public void shouldUpdateOrderStatus() throws InterruptedException {
        // Given there is an order in the system with paymentI3 = 33
        orderRepository.save(OrderEntity.builder().id(1).paymentId(33).status("CREATED").build());

        // When a paymentBooked event is released (for payment 33)
        stubTrigger.trigger("triggerPaymentBookedEvent");

        // Then order status should go to 'BOOKED'
        await()
                .timeout(3, SECONDS)
                .untilAsserted(() -> assertThat(orderRepository.findById(1).orElseThrow().getStatus()).isEqualTo("BOOKED"));
    }

    @Configuration
    static class TestConfig {

        @Bean
        MessageVerifierSender<Message<?>> standaloneMessageVerifier(KafkaTemplate kafkaTemplate) {
            return new MessageVerifierSender<Message<?>>() {

                @Override
                public void send(Message<?> message, String destination, @Nullable YamlContract contract) {
                    kafkaTemplate.send(message);
                }

                @Override
                public <T> void send(T payload, Map<String, Object> headers, String destination, @Nullable YamlContract contract) {
                    kafkaTemplate.send(createMessage(payload, new MessageHeaders(Map.of(TOPIC, destination))));
                }
            };
        }
    }
}
