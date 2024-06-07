package com.online.shop.ecombackend.dao;

import com.online.shop.ecombackend.model.WishlistItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface WishlistItemRepository extends JpaRepository<WishlistItem, Long> {
    List<WishlistItem> findByUser_Id(String userId);

    Optional<WishlistItem> findByUserIdAndProductId(String userId, int productId);


}
