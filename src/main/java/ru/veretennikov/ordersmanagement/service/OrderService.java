package ru.veretennikov.ordersmanagement.service;

import ru.veretennikov.ordersmanagement.domain.Order;

import java.util.List;
import java.util.Optional;

public interface OrderService {

    Optional<Order> getOrderById(Integer id);
    List<Order > getAllOrders();
    Order save(Order order);
    void delete(Order order);

}
