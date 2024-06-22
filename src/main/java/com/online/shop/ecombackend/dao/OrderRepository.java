package com.online.shop.ecombackend.dao;

import com.online.shop.ecombackend.dto.OrderDto;
import com.online.shop.ecombackend.model.Order;
import com.online.shop.ecombackend.model.OrderProjection;
import com.online.shop.ecombackend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUser(User user);

    @Query("SELECT  o.user.id AS userId FROM Order o")
    List<OrderProjection> findUserIdsFromOrders();
}
