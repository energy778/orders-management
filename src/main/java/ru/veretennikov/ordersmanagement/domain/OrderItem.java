package ru.veretennikov.ordersmanagement.domain;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
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

//    @NotNull(message = "Поле должно быть заполнено")
//    @Positive(message = "Цена должна быть больше 0")
    private Float price;

//    @NotNull(message = "Поле должно быть заполнено")
//    @Positive(message = "Количество должно быть больше 0")
    private Integer quantity;

//    @NotNull(message = "Поле должно быть заполнено")
//    @Positive(message = "Сумма должна быть больше 0")
    private Float sum;

}
