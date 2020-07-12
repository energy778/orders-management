package ru.veretennikov.ordersmanagement.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.veretennikov.ordersmanagement.domain.Goods;
import ru.veretennikov.ordersmanagement.domain.Order;
import ru.veretennikov.ordersmanagement.domain.OrderItem;
import ru.veretennikov.ordersmanagement.repository.GoodsRepository;
import ru.veretennikov.ordersmanagement.repository.OrderItemRepository;
import ru.veretennikov.ordersmanagement.repository.OrderRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
public class GreetingController {

    private final GoodsRepository goodsRepository;
    private final OrderItemRepository orderItemRepository;
    private final OrderRepository orderRepository;

    public GreetingController(GoodsRepository goodsRepository, OrderItemRepository orderItemRepository, OrderRepository orderRepository) {
        this.goodsRepository = goodsRepository;
        this.orderItemRepository = orderItemRepository;
        this.orderRepository = orderRepository;
    }

    @GetMapping("/greeting")
    public String greeting(@RequestParam(name="name", required=false, defaultValue="World") String name, Model model) {

        monkeyTest();

        model.addAttribute("name", name);
        return "greeting";

    }

    private void monkeyTest() {

//        1. попытка добавить строку в табличную часть
        OrderItem orderItem = new OrderItem();
        orderItem.setPrice(222f);
        orderItem.setQuantity(2);
        orderItem.setSum(444f);

        try {
            orderItemRepository.save(orderItem);
        } catch (Exception ex){
            System.out.println("ожидаемая ошибка в блоке 1");
//            ex.printStackTrace();
        }

//        2. добавление товара
        Goods goods = new Goods();
        goods.setName("Товар 1");
        goods.setPrice(222f);

        try {
            goodsRepository.save(goods);
        } catch (Exception ex){
            System.out.println("Ошибка в блоке 2");
//            ex.printStackTrace();
        }

//        3. проверка foreign key order в табличной части
        Order order = new Order();
        order.setCustomer("vasya@mail.ru");
        order.setNum(7L);
        order.setOrderTime(LocalDateTime.now());
        orderItem.setOrder(order);
        orderItem.setGoodsItem(goods);
        try {
            orderItemRepository.save(orderItem);
        } catch (Exception ex){
            System.out.println("ожидаемая ошибка в блоке 3");
//            ex.printStackTrace();
        }

//        4. проверка nullable order в табличной части
        orderItem.setOrder(null);
        try {
            orderItemRepository.save(orderItem);
        } catch (Exception ex){
            System.out.println("ожидаемая ошибка в блоке 4");
//            ex.printStackTrace();
        }

//        5. добавление заказа
        try {
            orderRepository.save(order);
        } catch (Exception ex){
            System.out.println("Ошибка в блоке 5");
//            ex.printStackTrace();
        }

//        6. запись состава заказа с существующим заказом
        orderItem.setOrder(order);
        try {
            orderItemRepository.save(orderItem);
        } catch (Exception ex){
            System.out.println("Ошибка в блоке 6");
//            ex.printStackTrace();
        }

//        7. добавление строки в состав заказа
        OrderItem orderItem2 = new OrderItem();
        orderItem2.setPrice(222f);
        orderItem2.setQuantity(2);
        orderItem2.setSum(444f);
        orderItem2.setGoodsItem(goods);
        orderItem2.setOrder(order);
        try {
            orderItemRepository.save(orderItem2);
        } catch (Exception ex){
            System.out.println("Ошибка в блоке 7");
//            ex.printStackTrace();
        }

//        8. добавление дублирующейся строки в состав заказа
        OrderItem orderItem3 = new OrderItem();
        orderItem3.setPrice(222f);
        orderItem3.setQuantity(2);
        orderItem3.setSum(444f);
        orderItem3.setGoodsItem(goods);
        orderItem3.setOrder(order);
        try {
            orderItemRepository.save(orderItem3);
        } catch (Exception ex){
            System.out.println("Ошибка в блоке 8");
//            ex.printStackTrace();
        }

//        9. проверка записи полного заказа вместе с табличной частью
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
        itemFirst.setOrder(orderFull);
        items.add(itemFirst);

        OrderItem itemSecond = new OrderItem();
        itemSecond.setGoodsItem(goods);
        itemSecond.setPrice(22f);
        itemSecond.setQuantity(1);
        itemSecond.setSum(22f);
        itemSecond.setOrder(orderFull);
        items.add(itemSecond);
        orderFull.setItems(items);

        try {
            orderRepository.save(orderFull);
        } catch (Exception ex){
            System.out.println("Ошибка в блоке 9");
//            ex.printStackTrace();
        }

//        10. проверка обновления заказа
        Order orderDB = orderRepository.getOne(2);
        orderDB.getItems().remove(1);
        OrderItem itemThird = new OrderItem();
        itemThird.setGoodsItem(goods);
        itemThird.setPrice(33f);
        itemThird.setQuantity(1);
        itemThird.setSum(33f);
        itemThird.setOrder(orderDB);
        orderDB.getItems().add(itemThird);
        try {
            orderRepository.save(orderDB);
        } catch (Exception ex){
            System.out.println("Ошибка в блоке 10");
//            ex.printStackTrace();
        }

//        11. проверка удаления строки из заказа
        OrderItem ordItemForDelete = orderItemRepository.getOne(6);
        Order ignored = ordItemForDelete.getOrder();
        try {
            orderItemRepository.delete(ordItemForDelete);
//            не удалилось
        } catch (Exception ex){
            System.out.println("ожидаемая ошибка в блоке 11");
//            ex.printStackTrace();
        }

//        12. проверка удаления заказа
//        ОШИБКА: UPDATE или DELETE в таблице "t_order" нарушает ограничение внешнего ключа "fk_parent" таблицы "tp_order_items"
//        Подробности: На ключ (id)=(1) всё ещё есть ссылки в таблице "tp_order_items".
        try {
            Order orderForDelete = orderRepository.getOne(1);
            List<OrderItem> items1 = orderForDelete.getItems();
            orderRepository.delete(orderForDelete);
        } catch (Exception ex){
            System.out.println("Ошибка в блоке 12");
//            ex.printStackTrace();
        }

    }

}
