package ru.veretennikov.ordersmanagement.domain;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.*;
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

    @NotNull(message = "Номер не может быть null")
    @Positive(message = "Номер должен быть больше нуля")
    private Long num;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @Column(name = "order_time", columnDefinition = "TIMESTAMP")
    @NotNull(message = "Дата заказа не может быть null")
//    @FutureOrPresent(message = "Дата заказа не может быть меньше текущей")
    private LocalDateTime orderTime;

    @Email(message = "Укажите электронную почту заказчика в корректном формате")
    @NotEmpty(message = "Адрес не может быть пустым")
    private String customer;

    @OneToMany(fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
//    @NotEmpty(message = "Состав заказа не может быть пустым")
    private List<OrderItem> items;

    public Order() {
        items = new ArrayList<>();
    }

}
