package ru.veretennikov.ordersmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.veretennikov.ordersmanagement.domain.Order;

public interface OrderRepository extends JpaRepository<Order, Integer> {
}
