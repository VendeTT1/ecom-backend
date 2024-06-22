package com.online.shop.ecombackend.controller;

import com.online.shop.ecombackend.dao.OrderRepository;
import com.online.shop.ecombackend.dto.OrderDto;
import com.online.shop.ecombackend.model.Order;
import com.online.shop.ecombackend.model.OrderProjection;
import com.online.shop.ecombackend.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderRepository orderRepository;

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Order>> getOrdersByUserId(@PathVariable String userId) {
        List<Order> orders = orderService.getOrdersByUserId(userId);
        return ResponseEntity.ok(orders);
    }
    @GetMapping("/all")
    public List<Order> getAllOrders(){
        return  orderRepository.findAll();
    }
    @GetMapping("/user_id")
    public List<OrderProjection> getUserIdsFromOrders() {
        return orderService.getUserIdsFromOrders();
    }

    @PostMapping("/{userId}")
    public Order createOrder(@RequestBody OrderDto orderDTO, @PathVariable String userId) {
        return orderService.createOrder(orderDTO);
    }


}
