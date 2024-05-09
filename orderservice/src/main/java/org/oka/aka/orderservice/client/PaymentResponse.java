package org.oka.aka.orderservice.client;

import lombok.Data;

import java.math.BigInteger;

@Data
public class PaymentResponse {
    Integer id;
    BigInteger amount;
    String currency;
    String type;
    String cardNumber;
    String status;
}
