package ru.veretennikov.ordersmanagement.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.veretennikov.ordersmanagement.domain.Goods;
import ru.veretennikov.ordersmanagement.domain.Order;
import ru.veretennikov.ordersmanagement.domain.OrderItem;
import ru.veretennikov.ordersmanagement.repository.GoodsRepository;
import ru.veretennikov.ordersmanagement.repository.OrderRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
public class GreetingController {

    private final GoodsRepository goodsRepository;
    private final OrderRepository orderRepository;

    public GreetingController(GoodsRepository goodsRepository, OrderRepository orderRepository) {
        this.goodsRepository = goodsRepository;
        this.orderRepository = orderRepository;
    }

    @GetMapping("/greeting")
    public String greeting(@RequestParam(name="name", required=false, defaultValue="World") String name, Model model) {

        monkeyTest();

        model.addAttribute("name", name);
        return "greeting";

    }

    private void monkeyTest() {

//        1. добавление товара
        OrderItem orderItem = new OrderItem();
        orderItem.setPrice(222f);
        orderItem.setQuantity(2);
        orderItem.setSum(444f);

        Goods goods = new Goods();
        goods.setName("Товар 1");
        goods.setPrice(222f);

        try {
            goodsRepository.save(goods);
        } catch (Exception ex){
            System.out.println("Ошибка в блоке 1");
//            ex.printStackTrace();
        }

        Order order = new Order();
        order.setCustomer("vasya@mail.ru");
        order.setNum(7L);
        order.setOrderTime(LocalDateTime.now());
        orderItem.setGoodsItem(goods);

//        2. добавление заказа
        try {
            orderRepository.save(order);
        } catch (Exception ex){
            System.out.println("Ошибка в блоке 2");
//            ex.printStackTrace();
        }

//        3. проверка записи полного заказа вместе с табличной частью
        Order orderFull = new Order();
        orderFull.setNum(11L);
        orderFull.setOrderTime(LocalDateTime.now());
        orderFull.setCustomer("customer@mail.com");
        List<OrderItem> items = new ArrayList<>();

        OrderItem itemFirst = new OrderItem();
        itemFirst.setGoodsItem(goods);
        itemFirst.setPrice(11f);
        itemFirst.setQuantity(1);
        itemFirst.setSum(11f);
        items.add(itemFirst);

        OrderItem itemSecond = new OrderItem();
        itemSecond.setGoodsItem(goods);
        itemSecond.setPrice(22f);
        itemSecond.setQuantity(1);
        itemSecond.setSum(22f);
        items.add(itemSecond);
        orderFull.setItems(items);

        try {
            orderRepository.save(orderFull);
        } catch (Exception ex){
            System.out.println("Ошибка в блоке 3");
//            ex.printStackTrace();
        }

//        4. проверка обновления заказа
        Order orderDB = orderRepository.getOne(2);
        orderDB.getItems().remove(1);
        OrderItem itemThird = new OrderItem();
        itemThird.setGoodsItem(goods);
        itemThird.setPrice(33f);
        itemThird.setQuantity(1);
        itemThird.setSum(33f);
        orderDB.getItems().add(itemThird);
        try {
            orderRepository.save(orderDB);
        } catch (Exception ex){
            System.out.println("Ошибка в блоке 4");
//            ex.printStackTrace();
        }

//        5. проверка удаления заказа
        try {
            Order orderForDelete = orderRepository.getOne(2);
            List<OrderItem> items1 = orderForDelete.getItems();
            orderRepository.delete(orderForDelete);
        } catch (Exception ex){
            System.out.println("Ошибка в блоке 5");
//            ex.printStackTrace();
        }

    }

}
