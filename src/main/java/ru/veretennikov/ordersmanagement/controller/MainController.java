package ru.veretennikov.ordersmanagement.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import ru.veretennikov.ordersmanagement.domain.Order;
import ru.veretennikov.ordersmanagement.dto.OrderDTO;
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

    @GetMapping("/goods")
    public ModelAndView goods(ModelAndView modelAndView){
        modelAndView.setViewName("goods.html");
        modelAndView.addObject("goods", goodsService.getAllGoods());
        return modelAndView;
    }

    @GetMapping("/orders")
    public ModelAndView orders(ModelAndView modelAndView){
        modelAndView.setViewName("orders.html");
        List<OrderDTO> allOrders = orderService.getAllOrders();
        modelAndView.addObject("orders", allOrders);
        modelAndView.addObject("sum", allOrders.stream().mapToDouble(OrderDTO::getSum).sum());
        return modelAndView;
    }

    @GetMapping("/orders/{orderId}")
    public ModelAndView order(@PathVariable Integer orderId, ModelAndView modelAndView){
        modelAndView.setViewName("order.html");
        modelAndView.addObject("order", orderService.getOrderById(orderId).orElse(new Order()));
        return modelAndView;
    }

    @PostMapping("/orders/{orderId}/delete")
    public String deleteOrder(@PathVariable Integer orderId){
        orderService.deleteById(orderId);
        return "redirect:/orders";
    }

//    @PostMapping("/orders/{orderId}")
//    public OrderDTO saveOrder(@RequestBody OrderDTO order,
//                            Model model){
//        return orderService.save(order);
//    }

}
