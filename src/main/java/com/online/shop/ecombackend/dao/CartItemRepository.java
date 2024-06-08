package com.online.shop.ecombackend.dao;

import com.online.shop.ecombackend.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
}
