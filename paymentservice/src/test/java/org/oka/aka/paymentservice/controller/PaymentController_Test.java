package org.oka.aka.paymentservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.oka.aka.paymentservice.model.Payment;
import org.oka.aka.paymentservice.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.oka.aka.paymentservice.PaymentGenerator.genPayment;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@AutoConfigureMockMvc
@Testcontainers
public class PaymentController_Test {
    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> DB = new PostgreSQLContainer<>("postgres:16-alpine");

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private PaymentRepository paymentRepository;
    @Autowired
    ObjectMapper mapper;

    @Test
    void shouldCreateANewPayment() throws Exception {
        // Given
        var payment = genPayment();

        // when
        ResultActions resultActions = this.mockMvc.perform(
                        post("/api/paymentservice/payments")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(mapper.writeValueAsString(payment)))
                .andDo(print())
                .andExpect(status().isOk());

        // Then
        Payment paymentResult = mapper.readValue(resultActions.andReturn().getResponse().getContentAsString(), Payment.class);
        Payment fromDb = paymentRepository.findById(paymentResult.getId()).orElseThrow();
        assertThat(payment.getCardNumber()).isEqualTo(fromDb.getCardNumber());
        assertThat(paymentResult.getStatus()).isEqualTo("CREATED");
    }
}
