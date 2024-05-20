package com.online.shop.ecombackend.service;

import com.online.shop.ecombackend.dao.OrderRepository;
import com.online.shop.ecombackend.dao.ProductRepository;

import com.online.shop.ecombackend.model.Product;
import com.online.shop.ecombackend.model.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    private ProductRepository productRepository;
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }
    public List<Product> getProduct(){
        return productRepository.findAll();
    }

}
