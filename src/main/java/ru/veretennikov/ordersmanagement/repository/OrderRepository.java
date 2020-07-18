package ru.veretennikov.ordersmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.veretennikov.ordersmanagement.domain.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
}
