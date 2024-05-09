package org.oka.aka.orderservice;

import org.jeasy.random.EasyRandom;
import org.jeasy.random.EasyRandomParameters;
import org.jeasy.random.FieldPredicates;
import org.jeasy.random.randomizers.time.DateRandomizer;
import org.oka.aka.orderservice.model.OrderEntity;

import java.math.BigInteger;
import java.util.Random;

import static java.util.Arrays.asList;
import static java.util.concurrent.ThreadLocalRandom.current;
import static org.apache.commons.lang3.RandomStringUtils.random;

public class OrderGenerator {
    static Random random = new Random(1L);
    static EasyRandomParameters parameters = new EasyRandomParameters()
            .randomize(FieldPredicates.named("id"), () -> null)
            .randomize(FieldPredicates.named("activityId"), () -> random.nextInt(500))
            .randomize(FieldPredicates.named("date"), new DateRandomizer())
            .randomize(FieldPredicates.named("clientName"), () -> asList("John Starks", "Mark Jackson").get(random.nextInt(1)))
            .randomize(FieldPredicates.named("address"), () -> asList("Random Strasse 12", "Can Colla 45").get(random.nextInt(1)))
            .randomize(FieldPredicates.named("phone"), () -> random(9, false, true))
            .randomize(FieldPredicates.named("cardNumber"), () -> random(16, false, true))
            .randomize(FieldPredicates.named("price"), () -> BigInteger.valueOf(random.nextInt(1000)))
            .randomize(FieldPredicates.named("currency"), () -> asList("CHF", "EUR").get(random.nextInt(1)))
            .randomize(FieldPredicates.named("status"), () -> null)
            .randomize(FieldPredicates.named("paymentId"), () -> null)
            .randomize(BigInteger.class, () -> BigInteger.valueOf(current().nextInt(10, 100 + 1)));
    static EasyRandom easyRandom = new EasyRandom(parameters);

    public static OrderEntity genOrder() {

        return easyRandom.nextObject(OrderEntity.class);
    }
}
