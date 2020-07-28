package ru.veretennikov.ordersmanagement.domain;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

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
//    @NotBlank(message = "Укажите товар")
    private Goods goodsItem;

//    @NotBlank(message = "Цена не может быть равной нулю")
//    @Positive(message = "Цена не может быть равной нулю")
    private Float price;

//    @NotBlank(message = "Количество не может быть равным нулю")
//    @Positive(message = "Количество не может быть равным нулю")
    private Integer quantity;

//    @NotBlank(message = "Сумма не может быть равной нулю")
//    @Positive(message = "Сумма не может быть равной нулю")
    private Float sum;

}
