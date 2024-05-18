package org.oka.aka.paymentservice.contracttest;

import au.com.dius.pact.provider.junit5.PactVerificationContext;
import au.com.dius.pact.provider.junitsupport.Provider;
import au.com.dius.pact.provider.junitsupport.State;
import au.com.dius.pact.provider.junitsupport.loader.PactBroker;
import au.com.dius.pact.provider.junitsupport.loader.PactBrokerAuth;
import au.com.dius.pact.provider.spring.junit5.MockMvcTestTarget;
import au.com.dius.pact.provider.spring.junit5.PactVerificationSpringProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.extension.ExtendWith;
import org.oka.aka.paymentservice.model.Payment;
import org.oka.aka.paymentservice.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.math.BigInteger;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@WebMvcTest
@AutoConfigureMockMvc
@Testcontainers
@Provider("payment-service")
@PactBroker(url = "http://localhost:9000", authentication = @PactBrokerAuth(username = "pact", password = "pact"))
@Tag("PactTest")
class PactContractVerificationTest {
    @MockBean
    private PaymentService paymentService;
    @Autowired
    private MockMvc mockMvc;

    @TestTemplate
    @ExtendWith(PactVerificationSpringProvider.class)
    void pactVerificationTestTemplate(PactVerificationContext context) {
        context.verifyInteraction();
    }

    @BeforeEach
    void beforeEach(PactVerificationContext context) {
        context.setTarget(new MockMvcTestTarget(mockMvc));
    }

    @State(value = {
            "There are no payments in the system",
            "I create a new payment"})
    public void iCreateANewPayment() {
        Payment payment = Payment.builder()
                .id(1)
                .amount(BigInteger.valueOf(15))
                .currency("CHF")
                .type("CREDIT_CARD")
                .cardNumber("1234567890123")
                .status("CREATED").build();
        when(paymentService.createPayment(any())).thenReturn(payment);
    }
}