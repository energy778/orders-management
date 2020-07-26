package ru.veretennikov.ordersmanagement.service;

import ru.veretennikov.ordersmanagement.domain.OrderItem;

import java.util.Optional;

public interface OrderItemService {
    void deleteById(Integer orderItemId);
    Optional<OrderItem> getOrderItemById(Integer orderItemId);
}
