package ru.veretennikov.ordersmanagement.domain;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "ref_goods")
public class Goods {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;
    private Float price;

}
