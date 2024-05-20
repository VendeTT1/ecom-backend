package com.online.shop.ecombackend.dao;

import com.online.shop.ecombackend.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {

}
