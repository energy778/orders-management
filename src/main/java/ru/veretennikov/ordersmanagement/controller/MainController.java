package ru.veretennikov.ordersmanagement.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.support.BindingAwareModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.veretennikov.ordersmanagement.domain.Order;
import ru.veretennikov.ordersmanagement.domain.OrderItem;
import ru.veretennikov.ordersmanagement.dto.OrderDTO;
import ru.veretennikov.ordersmanagement.service.GoodsService;
import ru.veretennikov.ordersmanagement.service.OrderService;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

    @GetMapping("/orders/add")
    public ModelAndView createOrder(ModelAndView modelAndView){
        modelAndView.setViewName("order.html");
        modelAndView.addObject("order", new Order());
        modelAndView.addObject("goods", goodsService.getAllGoods());
        return modelAndView;
    }

    @GetMapping("/orders/{orderId}")
    public ModelAndView updateOrder(@PathVariable(required = false) Integer orderId,
                                    ModelAndView modelAndView){

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
    public String saveOrder(@Valid @ModelAttribute("order") Order order,
//                            @Valid @ModelAttribute("orderItems") ArrayList<OrderItem> orderItems,
                            BindingResult bindingResult,
                            Model model){

//        тут может быть сохранение как нового заказа, так и существующего
//        проверка нужна и на тот случай, если какие-то параметры очистят в существующем заказе

        if (bindingResult.hasErrors()){

            Map<String, String> errorsMap;
//            if (isNull(order.getId()))
////                для новых заказов не нужно проверять наличие строк
//                errorsMap = ControllerUtils.getErrors(bindingResult, "items");
//            else
                errorsMap = ControllerUtils.getErrors(bindingResult);

////            после фильтра ошибок может не остаться
//            if (errorsMap.isEmpty()) {
//                bindingResult = new BeanPropertyBindingResult(order, "order");
////                bindingResult.getFieldErrors().clear();
//                model.addAttribute("org.springframework.validation.BindingResult.order", bindingResult);
////                bindingResult = new BeanPropertyBindingResult(order.getItems().get(0), "orderItem"); для строк
//            } else {
                model.mergeAttributes(errorsMap);
                return "order.html";
//            }

        }

        // TODO: 29.07.2020 проверить на ошибки сохранения в БД
        orderService.save(order);
        return "redirect:/orders";

    }

//    состав заказа

    @PostMapping("/orders/{orderId}/add")
    public ModelAndView addOrderItem(@PathVariable Integer orderId,
                                     @ModelAttribute("order") Order order,
                                     ModelAndView modelAndView){

        modelAndView.setViewName("order.html");

        // FIXME: 27.07.2020
//        order.getItems().add(new OrderItem());
        OrderItem newItem = new OrderItem();
        newItem.setId(-1);
        order.getItems().add(newItem);

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
