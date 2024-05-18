package org.oka.aka.orderservice.repository;

import org.oka.aka.orderservice.model.OrderEntity;
import org.springframework.data.repository.ListCrudRepository;

import java.util.Optional;

public interface OrderRepository extends ListCrudRepository<OrderEntity, Integer> {
    public Optional<OrderEntity> findByPaymentId(int id);
}
