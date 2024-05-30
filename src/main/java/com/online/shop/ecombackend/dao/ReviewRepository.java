package com.online.shop.ecombackend.dao;

import com.online.shop.ecombackend.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Integer> {
}
