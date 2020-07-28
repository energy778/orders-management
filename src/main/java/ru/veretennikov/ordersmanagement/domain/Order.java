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

//    @NotBlank(message = "Номер не может быть пустым")
//    @NotNull(message = "Номер не может быть null")
//    @NotEmpty(message = "Номер не может быть null")
//    @Null(message = "Номер не может быть null")
//    @Positive(message = "Номер не может быть равным нулю")
//    @PositiveOrZero(message = "Номер не может быть равным нулю")
    @Min(value = 0L, message = "The value must be positive")
    @NotNull
    private Long num;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @Column(name = "order_time", columnDefinition = "TIMESTAMP")
    @NotNull(message = "Дата заказа не может быть пустой")
//    @NotBlank(message = "Дата заказа не может быть пустой")
//    @NotEmpty(message = "Дата заказа не может быть пустой")
    private LocalDateTime orderTime;

    @Email(message = "Укажите электронную почту заказчика")
    @NotBlank(message = "Укажите электронную почту заказчика")
    private String customer;

    @OneToMany(fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
//    @NotBlank(message = "Состав заказа не может быть пустым")
    private List<OrderItem> items;

    public Order() {
        items = new ArrayList<>();
    }

}
