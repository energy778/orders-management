package ru.veretennikov.ordersmanagement.domain;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "tp_order_items")
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(optional=false)
    @JoinColumn(name = "goods_id")
    private Goods goodsItem;

    private Float price;
    private Integer quantity;
    private Float sum;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "order_id")
    private Order order;

}
