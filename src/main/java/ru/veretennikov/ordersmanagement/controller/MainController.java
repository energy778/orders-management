package ru.veretennikov.ordersmanagement.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.veretennikov.ordersmanagement.domain.Goods;
import ru.veretennikov.ordersmanagement.domain.Order;
import ru.veretennikov.ordersmanagement.service.GoodsService;
import ru.veretennikov.ordersmanagement.service.OrderService;

import java.util.List;

@RestController
public class MainController {

    private final OrderService orderService;
    private final GoodsService goodsService;

    public MainController(OrderService orderService, GoodsService goodsService) {
        this.orderService = orderService;
        this.goodsService = goodsService;
    }

//    @GetMapping("/greeting")
//    public String greeting(@RequestParam(name="name", required=false, defaultValue="World") String name,
//                           Model model) {
//        model.addAttribute("name", name);
//        return "greeting";
//    }

    @GetMapping("/orders")
//    @GetMapping(path = "/orders", consumes = "application/json", produces = "application/xml")
    public List<Order> orders(Model model){
        return orderService.getAllOrders();
    }

    @GetMapping("/orders/{orderId}")
    public Order order(@PathVariable Integer orderId,
                         Model model){
        return orderService.getOrderById(orderId).orElse(new Order());
    }

    @PostMapping("/orders/{orderId}")
    public Order saveOrder(@RequestBody Order order,
                            Model model){
        return orderService.save(order);
    }

    @PostMapping("/orders/{orderId}/delete")
    public String deleteOrder(@PathVariable Integer orderId,
                              Model model){
        orderService.deleteById(orderId);
        return "good job";
    }

    @GetMapping("/goods")
    public List<Goods> goods(Model model){
        return goodsService.getAllGoods();
    }

}
