package org.oka.aka.orderservice.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.oka.aka.orderservice.client.PaymentClient;
import org.oka.aka.orderservice.model.OrderEntity;
import org.oka.aka.orderservice.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {
    private final OrderRepository orderRepository;
    private final PaymentClient paymentClient;

    public List<OrderEntity> getOrders() {

        return orderRepository.findAll();
    }

    public OrderEntity getOrder(int id) {

        return orderRepository.findById(id).orElseThrow();
    }

    // TODO: This is not robust, 2 remote systems informed in the same time. Consider 2PC or TransactionalOutbox
    public OrderEntity createOrder(OrderEntity orderEntity) {
        orderEntity.setStatus("CREATED");
        Integer paymentId = paymentClient.createPayment(orderEntity.getPrice(), orderEntity.getCurrency(), "CREDIT_CARD", orderEntity.getCardNumber());
        orderEntity.setPaymentId(paymentId);

        return orderRepository.save(orderEntity);
    }

    public void updateOrder(int id, String newStatus) {
        // TODO: What happens if the id is not found ?
        orderRepository.findByPaymentId(id).ifPresent(order -> {
            order.setStatus(newStatus);
            orderRepository.save(order);
        });
    }
}
