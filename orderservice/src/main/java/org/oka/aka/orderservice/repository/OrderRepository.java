package org.oka.aka.orderservice.repository;

import org.oka.aka.orderservice.model.OrderEntity;
import org.springframework.data.repository.ListCrudRepository;

public interface OrderRepository extends ListCrudRepository<OrderEntity, Integer> {
}
