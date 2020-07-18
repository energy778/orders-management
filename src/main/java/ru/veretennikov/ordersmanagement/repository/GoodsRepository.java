package ru.veretennikov.ordersmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.veretennikov.ordersmanagement.domain.Goods;

@Repository
public interface GoodsRepository extends JpaRepository<Goods, Integer> {
}
