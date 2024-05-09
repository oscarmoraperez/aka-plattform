package org.oka.aka.paymentservice.contracttest;

import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.oka.aka.paymentservice.controller.PaymentController;
import org.oka.aka.paymentservice.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigInteger;

import static org.mockito.Mockito.when;
import static org.oka.aka.paymentservice.model.Payment.PaymentBuilder;
import static org.oka.aka.paymentservice.model.Payment.builder;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = PaymentController.class)
public class BaseContractTest {

    @Autowired
    PaymentController paymentController;
    @MockBean
    PaymentService paymentService;

    @BeforeEach
    public void setup() {
        PaymentBuilder paymentBuilder = builder()
                .amount(BigInteger.valueOf(15))
                .currency("CHF")
                .type("CREDIT_CARD")
                .cardNumber("1234567890123");
        when(paymentService
                .createPayment(paymentBuilder.build()))
                .thenReturn(paymentBuilder.id(1).status("CREATED").build());

        RestAssuredMockMvc.standaloneSetup(paymentController);
    }
}
