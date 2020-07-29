package ru.veretennikov.ordersmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.veretennikov.ordersmanagement.domain.Order;
import ru.veretennikov.ordersmanagement.dto.OrderDTO;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {

    @Query(value = "SELECT " +
            "new ru.veretennikov.ordersmanagement.dto.OrderDTO" +
            "(t1.id, t1.num, t1.orderTime, t1.customer, sum(coalesce(t2.sum, 0))) " +
            "FROM Order as t1 " +
            " LEFT JOIN t1.items as t2 " +
//            " WHERE t1.id = 2 " +
            " GROUP BY t1.id " +
            " ORDER BY t1.num"
    )
    List<OrderDTO> findAllWithSum();

}
