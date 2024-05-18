package org.oka.aka.orderservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.oka.aka.orderservice.client.PaymentClient;
import org.oka.aka.orderservice.model.OrderEntity;
import org.oka.aka.orderservice.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.oka.aka.orderservice.OrderGenerator.genOrder;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@AutoConfigureMockMvc
@Testcontainers
public class OrderEntityController_Test {

    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> DB = new PostgreSQLContainer<>("postgres:16-alpine").withReuse(true);
    @Container
    @ServiceConnection
    static KafkaContainer KAFKA = new KafkaContainer(DockerImageName.parse("confluentinc/cp-kafka:7.3.3")).withReuse(true);

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private OrderRepository repository;
    @Autowired
    ObjectMapper mapper;
    @MockBean
    PaymentClient paymentClient;

    @DynamicPropertySource
    static void overrideProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.kafka.bootstrap-servers", KAFKA::getBootstrapServers);
    }

    @Test
    void shouldCreateANewPayment() throws Exception {
        // Given
        var order = genOrder();
        // And
        when(paymentClient.createPayment(any(), any(), any(), any())).thenReturn(1);

        // when
        ResultActions resultActions = this.mockMvc.perform(
                        post("/api/orderservice/orders")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(mapper.writeValueAsString(order)))
                .andDo(print())
                .andExpect(status().isOk());

        // Then
        OrderEntity orderEntityResult = mapper.readValue(resultActions.andReturn().getResponse().getContentAsString(), OrderEntity.class);
        OrderEntity fromDb = repository.findById(orderEntityResult.getId()).orElseThrow();
        assertThat(fromDb.getCardNumber()).isEqualTo(order.getCardNumber());
        assertThat(fromDb.getPaymentId()).isOne();
        assertThat(fromDb.getStatus()).isEqualTo("CREATED");
    }
}
