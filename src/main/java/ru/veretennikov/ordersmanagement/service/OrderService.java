package ru.veretennikov.ordersmanagement.service;

import ru.veretennikov.ordersmanagement.dto.OrderDTO;

import java.util.List;

public interface OrderService {

    List<OrderDTO> getAllOrders();
//    Optional<OrderDTO> getOrderById(Integer id);
//    OrderDTO save(OrderDTO order);
//    void deleteById(Integer orderId);

}
