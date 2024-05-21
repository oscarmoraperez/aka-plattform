package org.oka.aka.orderservice.client;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigInteger;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PaymentClient {

    private final RestTemplate restTemplate;

    public PaymentResponse createPayment(BigInteger amount, String currency, String type, String cardNumber) {
        var paymentRequest = PaymentRequest.builder().amount(amount).currency(currency).type(type).cardNumber(cardNumber).build();
        var paymentResponse = restTemplate.postForEntity("/api/paymentservice/payments", paymentRequest, PaymentResponse.class);

        return paymentResponse.getBody();
    }
}
