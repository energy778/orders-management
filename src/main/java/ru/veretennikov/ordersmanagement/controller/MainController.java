package ru.veretennikov.ordersmanagement.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.ModelAndView;
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

    @GetMapping("/orders")
    public ModelAndView orders(ModelAndView modelAndView){

        modelAndView.setViewName("orders.html");
        modelAndView.addObject("orders", orderService.getAllOrders());
        modelAndView.addObject("name", "sergio");

        return modelAndView;

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
