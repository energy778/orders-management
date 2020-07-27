package ru.veretennikov.ordersmanagement.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.veretennikov.ordersmanagement.domain.Order;
import ru.veretennikov.ordersmanagement.domain.OrderItem;
import ru.veretennikov.ordersmanagement.dto.OrderDTO;
import ru.veretennikov.ordersmanagement.service.GoodsService;
import ru.veretennikov.ordersmanagement.service.OrderService;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@Controller
public class MainController {

    private final OrderService orderService;
    private final GoodsService goodsService;

    public MainController(OrderService orderService, GoodsService goodsService) {
        this.orderService = orderService;
        this.goodsService = goodsService;
    }

//    список товаров

    @GetMapping("/goods")
    public ModelAndView goods(ModelAndView modelAndView){
        modelAndView.setViewName("goods.html");
        modelAndView.addObject("goods", goodsService.getAllGoods());
        return modelAndView;
    }

//    список заказов

    @RequestMapping(value = {"/", "/orders"}, method = RequestMethod.GET)
    public ModelAndView orders(ModelAndView modelAndView){
        modelAndView.setViewName("orders.html");
        List<OrderDTO> allOrders = orderService.getAllOrders();
        modelAndView.addObject("orders", allOrders);
        modelAndView.addObject("sum", allOrders.stream().mapToDouble(OrderDTO::getSum).sum());
        return modelAndView;
    }

//    заказ

    @GetMapping(value = {"/orders/{orderId}", "/orders/add"})
    public ModelAndView updateOrder(@PathVariable(required = false) Integer orderId, ModelAndView modelAndView){

        modelAndView.setViewName("order.html");

        if (nonNull(orderId))
            modelAndView.addObject("order", orderService.getOrderById(orderId).orElse(new Order()));
        else
            modelAndView.addObject("order", new Order());

        modelAndView.addObject("goods", goodsService.getAllGoods());

        return modelAndView;

    }

    @PostMapping("/orders/{orderId}/delete")
    public String deleteOrder(@PathVariable Integer orderId){
        orderService.deleteById(orderId);
        return "redirect:/orders";
    }

    @PostMapping("/orders")
    public String saveOrder(@ModelAttribute("order") Order order){
        Order orderDB = orderService.save(order);
//        if (isNull(orderDB))
//            ошибка сохранения объекта - отдать на клиент
//        return "redirect:/orders/" + orderDB.getId();
        return "redirect:/orders";
    }

//    состав заказа

    @PostMapping(value = {"/orders/{orderId}/add"})
    public ModelAndView addOrderItem(@PathVariable Integer orderId,
                                     @ModelAttribute("order") Order order,
                                     ModelAndView modelAndView){

        modelAndView.setViewName("order.html");
        OrderItem newItem = new OrderItem();
        newItem.setId(-1);
        order.getItems().add(newItem);

//        временная заглушка
        order.setItems(order.getItems().stream()
                .peek(orderItem -> {
                    if (isNull(orderItem.getId()))
                        orderItem.setId(-1);
                }).collect(Collectors.toList()));

        modelAndView.addObject("order", order);
        modelAndView.addObject("goods", goodsService.getAllGoods());

        return modelAndView;

    }

    @PostMapping("/orders/{orderId}/{orderItemId}/delete")
    public ModelAndView deleteOrderItem(@PathVariable Integer orderItemId,
                                        @ModelAttribute("order") Order order,
                                        ModelAndView modelAndView){

        modelAndView.setViewName("order.html");
        order.setItems(order.getItems().stream()
                .filter(orderItem -> !orderItem.getId().equals(orderItemId))
                .collect(Collectors.toList()));

        modelAndView.addObject("order", order);
        modelAndView.addObject("goods", goodsService.getAllGoods());

        return modelAndView;

    }

}
