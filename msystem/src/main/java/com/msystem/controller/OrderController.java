package com.msystem.controller;

import com.msystem.entity.Order;
import com.msystem.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * @Description: Order的控制類
 * @author: Eker
 * @date: 2023/4/21 上午 12:47
 * @version: V1.0
 */
@CrossOrigin
@RestController
@RequestMapping("/api")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @GetMapping("/order")
    public List<Order> queryAllOrder(){
        List<Order> orderList = orderService.queryAllOrder();
        return orderList;
    }
    @GetMapping("/order/{id}")
    public Order queryOrderById(@PathVariable("id") Integer id){
        Optional<Order> order = orderService.queryOrderById(id);
        return order.orElse(null);
    }

}
