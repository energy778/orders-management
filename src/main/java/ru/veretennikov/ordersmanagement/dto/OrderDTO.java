package ru.veretennikov.ordersmanagement.dto;

import lombok.Data;
import ru.veretennikov.ordersmanagement.domain.OrderItem;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class OrderDTO {

    private Integer id;
    private Long num;
    private LocalDateTime orderTime;
    private String customer;
    private Double sum;
    private List<OrderItem> items;

    public OrderDTO() {
        items = new ArrayList<>();
    }

    public OrderDTO(Integer id, Long num, LocalDateTime orderTime, String customer, Double sum) {
        this.id = id;
        this.num = num;
        this.orderTime = orderTime;
        this.customer = customer;
        this.sum = sum;
    }

}
