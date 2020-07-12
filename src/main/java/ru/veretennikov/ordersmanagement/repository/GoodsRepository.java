package ru.veretennikov.ordersmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.veretennikov.ordersmanagement.domain.Goods;

public interface GoodsRepository extends JpaRepository<Goods, Integer> {
}
