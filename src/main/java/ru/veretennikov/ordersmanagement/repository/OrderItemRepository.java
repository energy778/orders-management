package ru.veretennikov.ordersmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.veretennikov.ordersmanagement.domain.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Integer> {
}
