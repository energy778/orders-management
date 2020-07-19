package ru.veretennikov.ordersmanagement.service;

import org.springframework.stereotype.Service;
import ru.veretennikov.ordersmanagement.dto.OrderDTO;
import ru.veretennikov.ordersmanagement.repository.OrderRepository;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository repository;

    public OrderServiceImpl(OrderRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<OrderDTO> getAllOrders() {
        return repository.findAllWithSum();
    }

//    @Override
//    public Optional<OrderDTO> getOrderById(Integer id) {
//        return repository.findById(id);
//    }
//
//    @Override
//    public void deleteById(Integer orderId) {
//        repository.deleteById(orderId);
//    }
//
//    @Override
//    public Order save(Order order) {
//        return repository.save(order);
//    }

}
