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
    @JoinColumn(name = "goods_id",
            nullable = false,
            foreignKey = @ForeignKey(name =  "fk_goods"))
    private Goods goodsItem;

    private Float price;
    private Integer quantity;
    private Float sum;

}
