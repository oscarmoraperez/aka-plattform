package org.oka.aka.orderservice.listener;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.oka.aka.orderservice.service.OrderService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import static org.oka.aka.orderservice.configuration.OrderServiceConfiguration.TOPIC_PAYMENTS;

@Component
@RequiredArgsConstructor
@Slf4j
public class PaymentMessageListener {
    private final OrderService orderService;
    private final ObjectMapper objectMapper;

    @KafkaListener(topics = TOPIC_PAYMENTS, groupId = "order-service")
    public void listenPaymentEvent(String data) throws JsonProcessingException {
        log.info("Event received: " + data);
        Payment payment = objectMapper.readValue(data, Payment.class);

        orderService.updateOrder(payment.getId(), payment.getStatus());
    }
}
