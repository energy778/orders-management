package ru.veretennikov.ordersmanagement.domain;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "t_order")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Long num;

    @Column(name = "order_time",
            columnDefinition = "TIMESTAMP")
    private LocalDateTime orderTime;

    private String customer;

    @OneToMany(fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<OrderItem> items;

    public Order() {
        items = new ArrayList<>();
    }

}
