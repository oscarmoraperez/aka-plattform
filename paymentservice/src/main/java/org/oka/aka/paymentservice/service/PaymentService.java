package org.oka.aka.paymentservice.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.oka.aka.paymentservice.model.Payment;
import org.oka.aka.paymentservice.repository.PaymentRepository;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import static org.oka.aka.paymentservice.configuration.PaymentServiceConfiguration.TOPIC_PAYMENTS;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentService {
    private final PaymentRepository paymentRepository;
    private final KafkaTemplate<String, Object> kafkaTemplate;

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
        log.info("Booking payments in status CREATED");
        var createdPayments = paymentRepository.findPaymentsByStatus("CREATED");
        for (Payment payment : createdPayments) {
            log.info("Booking payment: " + payment.getId() + "(" + payment.getAmount() + ")");
            Thread.sleep(1000); // Only simulate a call to a remote Payment gateway
            payment.setStatus("BOOKED");

            CompletableFuture<SendResult<String, Object>> future = kafkaTemplate.send(TOPIC_PAYMENTS, payment);
            CompletableFuture<String> rs = future.thenApply(f -> {
                var metadata = f.getRecordMetadata();
                var partition = metadata.partition();
                var offset = metadata.offset();
                log.info("Sent message=[{}] with offset=[{}]", payment, offset);
                return String.format("--------> %d-%d", partition, offset);
            }).exceptionally(err -> {
                log.info("Unable to send message=[{}] due to : {}", payment, err.getMessage());
                return null;
            });
        }
    }
}
