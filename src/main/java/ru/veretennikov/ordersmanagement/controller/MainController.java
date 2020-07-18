package ru.veretennikov.ordersmanagement.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.veretennikov.ordersmanagement.domain.Goods;
import ru.veretennikov.ordersmanagement.domain.Order;
import ru.veretennikov.ordersmanagement.service.GoodsService;
import ru.veretennikov.ordersmanagement.service.OrderService;

import java.util.List;

@Controller
public class MainController {

    private final OrderService orderService;
    private final GoodsService goodsService;

    public MainController(OrderService orderService, GoodsService goodsService) {
        this.orderService = orderService;
        this.goodsService = goodsService;
    }

    @GetMapping("/greeting")
    public String greeting(@RequestParam(name="name", required=false, defaultValue="World") String name,
                           Model model) {
        model.addAttribute("name", name);
        return "greeting";
    }

    @GetMapping("/orders")
//    @GetMapping(path = "/orders", consumes = "application/json", produces = "application/json")
    public String orders(Model model){
        List<Order> orders = orderService.getAllOrders();
        model.addAttribute("orders", orders);
        return "orders";
    }

    @GetMapping("/orders/{orderId}")
    public String order(@PathVariable Integer orderId,
                         Model model){
        Order order = orderService.getOrderById(orderId).orElse(new Order());
        model.addAttribute("order", order);
        return "order";
    }

    @PostMapping("/orders/{orderId}")
    public String saveOrder(@PathVariable Integer orderId,
                            @RequestParam Order order,
                            Model model){
        // почему маппится по id?
        orderService.save(order);
        return "order";
    }

    @DeleteMapping("/orders/{orderId}")
    public String deleteOrder(@PathVariable Integer orderId,
                              Model model){
        orderService.deleteById(orderId);
        return "order";
    }

    @GetMapping("/goods")
    public String goods(Model model){
        List<Goods> goods = goodsService.getAllGoods();
        model.addAttribute("goods", goods);
        return "goods";
    }

}
