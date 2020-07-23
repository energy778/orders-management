package ru.veretennikov.ordersmanagement.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.veretennikov.ordersmanagement.domain.Order;
import ru.veretennikov.ordersmanagement.dto.OrderDTO;
import ru.veretennikov.ordersmanagement.service.GoodsService;
import ru.veretennikov.ordersmanagement.service.OrderService;

import java.util.List;
import java.util.Objects;

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

    @RequestMapping(value = {"/", "/orders"}, method = RequestMethod.GET)
    public ModelAndView orders(ModelAndView modelAndView){
        modelAndView.setViewName("orders.html");
        List<OrderDTO> allOrders = orderService.getAllOrders();
        modelAndView.addObject("orders", allOrders);
        modelAndView.addObject("order", new Order());
        modelAndView.addObject("sum", allOrders.stream().mapToDouble(OrderDTO::getSum).sum());
        return modelAndView;
    }

    @GetMapping(value = {"/orders/{orderId}", "/orders/add"})
    public ModelAndView order(@PathVariable(required = false) Integer orderId, ModelAndView modelAndView){

        modelAndView.setViewName("order.html");

        if (!Objects.isNull(orderId))
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
        orderService.save(order);
        return "redirect:/orders";
    }

}
