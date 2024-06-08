package com.online.shop.ecombackend.service;

import com.online.shop.ecombackend.dao.CartItemRepository;
import com.online.shop.ecombackend.dao.OrderRepository;
import com.online.shop.ecombackend.dao.UserRepository;
import com.online.shop.ecombackend.dto.CartItemDto;
import com.online.shop.ecombackend.dto.OrderDto;
import com.online.shop.ecombackend.model.CartItem;
import com.online.shop.ecombackend.model.Order;
import com.online.shop.ecombackend.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CartItemRepository cartItemRepository;


    public Order createOrder(OrderDto orderDto) {
        User user = userRepository.findById(orderDto.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Order order = new Order();
        CartItem cartItem = null;
        if (orderDto.getCartItem() != null) {
            for (CartItemDto cartItemDto : orderDto.getCartItem()) {
                cartItem = new CartItem();
                cartItem.setProductId(cartItemDto.getProductId());
                cartItem.setTitle(cartItemDto.getTitle());
                cartItem.setImage(cartItemDto.getImage());
                cartItem.setRating(cartItemDto.getRating());
                cartItem.setPrice(cartItemDto.getPrice());
                cartItem.setBrandname(cartItemDto.getBrandname());
                cartItem.setAmount(cartItemDto.getAmount());
                cartItem.setSelectedsize(cartItemDto.getSelectedsize());
                cartItem.setIsinwishlist(cartItemDto.isisinwishlist());

            }
        } else {
            throw new RuntimeException("Cart items cannot be null");
        }

        order.setOrderstatus(orderDto.getOrderstatus());
        order.setSubtotal(orderDto.getSubtotal());
        order.setUser(user);
        order = orderRepository.save(order);
        cartItem.setOrders(order);
        cartItemRepository.save(cartItem);
        return order;
    }
    public List<Order> getOrdersByUserId(String userId) {
        // Fetch the user by ID
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Fetch orders by user
        return orderRepository.findByUser(user);
    }
}
