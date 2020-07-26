package ru.veretennikov.ordersmanagement.service;

import org.springframework.stereotype.Service;
import ru.veretennikov.ordersmanagement.domain.OrderItem;
import ru.veretennikov.ordersmanagement.repository.OrderItemRepository;

import java.util.Optional;

@Service
public class OrderItemServiceImpl implements OrderItemService {

    private final OrderItemRepository orderItemRepository;

    public OrderItemServiceImpl(OrderItemRepository orderItemRepository) {
        this.orderItemRepository = orderItemRepository;
    }

    @Override
    public void deleteById(Integer orderItemId) {
        orderItemRepository.deleteById(orderItemId);
    }

    @Override
    public Optional<OrderItem> getOrderItemById(Integer orderItemId) {
        return orderItemRepository.findById(orderItemId);
    }

}
