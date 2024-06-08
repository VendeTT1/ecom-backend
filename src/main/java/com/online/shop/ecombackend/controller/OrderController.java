package com.online.shop.ecombackend.controller;

import com.online.shop.ecombackend.dto.OrderDto;
import com.online.shop.ecombackend.model.Order;
import com.online.shop.ecombackend.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Order>> getOrdersByUserId(@PathVariable String userId) {
        List<Order> orders = orderService.getOrdersByUserId(userId);
        return ResponseEntity.ok(orders);
    }
    @PostMapping
    public Order createOrder(@RequestBody OrderDto orderDTO) {
        return orderService.createOrder(orderDTO);
    }
}
