package com.online.shop.ecombackend.controller;
import com.online.shop.ecombackend.dao.ProductRepository;
import com.online.shop.ecombackend.model.Product;
import com.online.shop.ecombackend.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Controller to handle the creation, updating & viewing of products.
 */
@RestController
@RequestMapping("/product")
public class ProductController {
    /** The Product Service. */
    @Autowired
    private ProductService productService;

    @Autowired
    private ProductRepository productRepository;

    /**
     * Constructor for spring injection.
     * @param productService
     */
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    /**
     * Gets the list of products available.
     * @return The list of products.
     */
    @GetMapping("/all")
    public List<Product> getProducts() {
        return productService.getProduct();
    }

    /*
    * this function is been used by the landing.jsx using params to display 8 product
    * */
    @GetMapping //("/products")
    public List<Product> getProducts(
            @RequestParam(name = "page") int page,
            @RequestParam(name = "limit") int limit) {
        return productService.getProducts(page, limit);
    }
    @GetMapping("/{id}")
    public Optional<Product> getProducts(@PathVariable("id") Integer id) {
        return productRepository.findById(id);
    }
}
