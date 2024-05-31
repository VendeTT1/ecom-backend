package com.online.shop.ecombackend.controller;


import com.online.shop.ecombackend.dao.ProductRepository;
import com.online.shop.ecombackend.dto.ProductSpecification;
import com.online.shop.ecombackend.model.Product;
import com.online.shop.ecombackend.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
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
    public List<Product> getSingleProductByid(@PathVariable("id") Integer id) {
        return productRepository.findByReviewProductId(id);
    }


    @GetMapping("/shop")
    public Page<Product> getProducts(
            @RequestParam(defaultValue = "0") int start,
            @RequestParam(defaultValue = "10") int limit,
            @RequestParam(required = false) String brandname,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String gender,
            @RequestParam(required = false) String sort,
            @RequestParam(required = false) Boolean isinstock,
            @RequestParam(required = false) Double price,
            @RequestParam(required = false) String productiondate) {

        Pageable pageable = PageRequest.of(start / limit, limit);

        Specification<Product> spec = Specification.where(ProductSpecification.hasBrand(brandname))
                .and(ProductSpecification.hasCategory(category))
                .and(ProductSpecification.hasGender(gender))
                .and(ProductSpecification.hasPriceLessThanOrEqual(price))
                .and(ProductSpecification.isInStock(isinstock))
                .and(ProductSpecification.hasProductionDate(productiondate));

        if (sort != null) {
            // Add sorting logic if needed

        }

        return productService.getProducts(spec, pageable);
    }
}
