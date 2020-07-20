package ru.veretennikov.ordersmanagement.service;

import ru.veretennikov.ordersmanagement.domain.Order;
import ru.veretennikov.ordersmanagement.dto.OrderDTO;

import java.util.List;
import java.util.Optional;

public interface OrderService {

    List<OrderDTO> getAllOrders();
    Optional<Order> getOrderById(Integer id);
    void deleteById(Integer orderId);
//    OrderDTO save(OrderDTO order);

}
