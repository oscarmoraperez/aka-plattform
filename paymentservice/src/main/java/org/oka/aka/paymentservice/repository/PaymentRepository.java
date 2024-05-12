package org.oka.aka.paymentservice.repository;

import org.oka.aka.paymentservice.model.Payment;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentRepository extends ListCrudRepository<Payment, Integer> {
    List<Payment> findPaymentsByStatus(String status);
}
