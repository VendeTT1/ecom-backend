package com.online.shop.ecombackend.service;

import com.online.shop.ecombackend.dao.OrderRepository;
import com.online.shop.ecombackend.model.Order;
import com.online.shop.ecombackend.model.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {
    private OrderRepository orderRepository;
    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }
    public List<Order> getOrders(User user){
        return orderRepository.findByUser(user);
    }

}
