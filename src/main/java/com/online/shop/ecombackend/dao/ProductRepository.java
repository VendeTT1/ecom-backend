package com.online.shop.ecombackend.dao;

import com.online.shop.ecombackend.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Integer> ,JpaSpecificationExecutor<Product> {
    List<Product> findAll();
    @Query("SELECT DISTINCT p FROM Product p JOIN reviews r ON p.id = r.product.id WHERE r.product.id = :id")
    List<Product> findByReviewProductId(@Param("id") Integer id);

    Page<Product> findByNameContainingIgnoreCaseOrBrandnameContainingIgnoreCase(String name, String brandname, Pageable pageable);
}
