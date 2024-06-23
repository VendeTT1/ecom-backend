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
import java.util.Optional;
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

    @PutMapping("/edit/{orderId}")
    public ResponseEntity<Order> updateOrder(@PathVariable Long orderId, @RequestBody Order updatedOrder) {
        Optional<Order> optionalOrder = orderRepository.findById(orderId);
        if (optionalOrder.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Order existingOrder = optionalOrder.get();

        // Update orderstatus if provided, otherwise keep existing value
        if (updatedOrder.getOrderstatus() != null) {
            existingOrder.setOrderstatus(updatedOrder.getOrderstatus());
        }

        // Update subtotal if provided, otherwise keep existing value
        if (updatedOrder.getSubtotal() != 0.0) {
            existingOrder.setSubtotal(updatedOrder.getSubtotal());
        }

        // Update cartItems if provided, otherwise keep existing value
        if (updatedOrder.getCartItem() != null && !updatedOrder.getCartItem().isEmpty()) {
            existingOrder.setCartItem(updatedOrder.getCartItem());
        }

        // Save the updated order
        Order savedOrder = orderRepository.save(existingOrder);
        return ResponseEntity.ok(savedOrder);
    }

    @DeleteMapping("/{orderId}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long orderId) {
        orderService.deleteOrderById(orderId);
        return ResponseEntity.noContent().build();
    }
}
