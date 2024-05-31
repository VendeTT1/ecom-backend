package com.online.shop.ecombackend.service;

import com.online.shop.ecombackend.dao.ProductRepository;
import com.online.shop.ecombackend.dao.ReviewRepository;
import com.online.shop.ecombackend.model.Product;
import com.online.shop.ecombackend.model.Review;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ReviewRepository   reviewRepository;
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }
    public List<Product> getProduct(){
        return productRepository.findAll();
    }

    public List<Product> getProducts(int page, int limit) {
        PageRequest pageRequest = PageRequest.of(page - 1, limit);
        Page<Product> productPage = productRepository.findAll(pageRequest);
        return productPage.getContent();
    }

    public Optional<Product> getProductById(int id) {
        return productRepository.findById(id);
    }

    public List<Product> getSingleProductById(int id) {
        return productRepository.findByReviewProductId(id);
    }

    public void addReviewToProduct(int productId, Review review) {
        Product product = productRepository.findById(productId).orElse(null);
        if (product != null) {
            review.setProduct(product);
            reviewRepository.save(review);
        }
    }
    public Page<Product> getProducts(Specification<Product> spec, Pageable pageable) {
        return productRepository.findAll(spec, pageable);
    }
}


