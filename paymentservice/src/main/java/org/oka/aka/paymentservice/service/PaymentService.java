package org.oka.aka.paymentservice.service;

import lombok.RequiredArgsConstructor;
import org.oka.aka.paymentservice.model.Payment;
import org.oka.aka.paymentservice.repository.PaymentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PaymentService {
    private final PaymentRepository paymentRepository;

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
}
