package org.oka.aka.paymentservice;

import org.jeasy.random.EasyRandom;
import org.jeasy.random.EasyRandomParameters;
import org.jeasy.random.FieldPredicates;
import org.oka.aka.paymentservice.model.Payment;

import java.math.BigInteger;
import java.util.Random;

import static java.util.Arrays.asList;
import static java.util.concurrent.ThreadLocalRandom.current;
import static org.apache.commons.lang3.RandomStringUtils.randomNumeric;

public class PaymentGenerator {
    static Random random = new Random(1L);
    static EasyRandomParameters parameters = new EasyRandomParameters()
            .randomize(FieldPredicates.named("id"), () -> null)
            .randomize(FieldPredicates.named("currency"), () -> asList("CHF", "EUR").get(random.nextInt(1)))
            .randomize(FieldPredicates.named("type"), () -> asList("CREDIT_CARD", "TRANSFER").get(random.nextInt(1)))
            .randomize(FieldPredicates.named("cardNumber"), () -> randomNumeric(16))
            .randomize(FieldPredicates.named("status"), () -> null)
            .randomize(BigInteger.class, () -> BigInteger.valueOf(current().nextInt(10, 100 + 1)));
    static EasyRandom easyRandom = new EasyRandom(parameters);

    public static Payment genPayment() {

        return easyRandom.nextObject(Payment.class);
    }
}
