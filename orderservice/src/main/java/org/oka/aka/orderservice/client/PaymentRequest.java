package org.oka.aka.orderservice.client;

import lombok.Builder;
import lombok.Value;

import java.math.BigInteger;

@Value
@Builder
public class PaymentRequest {
    BigInteger amount;
    String currency;
    String type;
    String cardNumber;
}
