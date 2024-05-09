package org.oka.aka.paymentservice.repository;

import org.oka.aka.paymentservice.model.Payment;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends ListCrudRepository<Payment, Integer> {
}
