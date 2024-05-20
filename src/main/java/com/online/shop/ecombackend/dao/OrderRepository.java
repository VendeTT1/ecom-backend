package com.online.shop.ecombackend.dao;

import com.online.shop.ecombackend.model.Order;
import com.online.shop.ecombackend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findByUser(User user);

}
