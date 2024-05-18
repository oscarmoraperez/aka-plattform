package org.oka.aka.paymentservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.oka.aka.paymentservice.model.Payment;
import org.oka.aka.paymentservice.repository.PaymentRepository;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import static org.oka.aka.paymentservice.configuration.PaymentServiceConfiguration.TOPIC_PAYMENTS;
import static org.springframework.kafka.support.KafkaHeaders.TOPIC;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentService {
    private final PaymentRepository paymentRepository;
    private final KafkaTemplate<Object, Object> kafkaTemplate;
    private final ObjectMapper objectMapper;

    public Payment retrievePayment(Integer id) {

        return paymentRepository.findById(id).orElseThrow();
    }

    public List<Payment> retrievePayments() {

        return paymentRepository.findAll();
    }

    public Payment createPayment(Payment payment) {

        payment.setStatus("CREATED");
        return paymentRepository.save(payment);
    }

    @Transactional
    @Scheduled(fixedRate = 5000)
    // TODO: Add transactional outbox and distributed locking
    public void processPayments() throws InterruptedException {
        var createdPayments = paymentRepository.findPaymentsByStatus("CREATED");
        for (Payment payment : createdPayments) {
            log.info("Booking payment: " + payment.getId() + "(" + payment.getAmount() + ")");
            Thread.sleep(1000); // Only simulate a call to a remote Payment gateway
            payment.setStatus("BOOKED");

            sendPayment(payment);
        }
    }

    public void sendPayment(Payment payment) {
        try {
            kafkaTemplate.send(new GenericMessage<>(objectMapper.writeValueAsString(payment), Map.of(TOPIC, TOPIC_PAYMENTS)));
        } catch (JsonProcessingException e) {
            throw new RuntimeException("It was not possible to read the message");
        }
    }
}
