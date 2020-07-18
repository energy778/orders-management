package ru.veretennikov.ordersmanagement.service;

import ru.veretennikov.ordersmanagement.domain.Goods;

import java.util.List;
import java.util.Optional;

public interface GoodsService {
    List<Goods> getAllGoods();
    Optional<Goods> getGoodsById(Integer id);
}
