package ru.veretennikov.ordersmanagement.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.veretennikov.ordersmanagement.domain.Order;
import ru.veretennikov.ordersmanagement.domain.OrderItem;
import ru.veretennikov.ordersmanagement.dto.OrderDTO;
import ru.veretennikov.ordersmanagement.service.GoodsService;
import ru.veretennikov.ordersmanagement.service.OrderItemService;
import ru.veretennikov.ordersmanagement.service.OrderService;

import java.util.List;

import static java.util.Objects.nonNull;

@Controller
public class MainController {

    private final OrderService orderService;
    private final GoodsService goodsService;
    private final OrderItemService orderItemService;

    public MainController(OrderService orderService, GoodsService goodsService, OrderItemService orderItemService) {
        this.orderService = orderService;
        this.goodsService = goodsService;
        this.orderItemService = orderItemService;
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

    @GetMapping(value = {"/orders/{orderId}/{orderItemId}", "/orders/{orderId}/add"})
    public ModelAndView updateOrderItem(@PathVariable(required = false) Integer orderItemId, ModelAndView modelAndView){

        modelAndView.setViewName("orderItem.html");

        if (nonNull(orderItemId))
            modelAndView.addObject("order", orderItemService.getOrderItemById(orderItemId).orElse(new OrderItem()));
        else
            modelAndView.addObject("order", new OrderItem());

        modelAndView.addObject("goods", goodsService.getAllGoods());

        return modelAndView;

    }

    @PostMapping("/orders/{orderId}/{orderItemId}/delete")
    public String deleteOrderItem(@PathVariable Integer orderItemId){
        orderItemService.deleteById(orderItemId);
        return "redirect:/orders";
    }

}
