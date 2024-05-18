package org.oka.aka.orderservice.listener;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.oka.aka.orderservice.model.Payment;
import org.oka.aka.orderservice.service.OrderService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import static org.oka.aka.orderservice.configuration.OrderServiceConfiguration.TOPIC_PAYMENTS;

@Component
@RequiredArgsConstructor
@Slf4j
public class PaymentMessageListener {
    private final OrderService orderService;
    @KafkaListener(topics = TOPIC_PAYMENTS, groupId = "order-service")
    public void listenNotification(Payment data) {
        log.info("Event received: " + data);

        orderService.updateOrder(data.getId(), data.getStatus());
    }
}
