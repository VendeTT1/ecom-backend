package com.online.shop.ecombackend.dto;

import com.online.shop.ecombackend.model.Product;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

public class ProductSpecification {

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSSSS");

    public static Specification<Product> hasBrand(String brand) {
        return (root, query, criteriaBuilder) -> brand != null ? criteriaBuilder.equal(root.get("brandname"), brand) : criteriaBuilder.conjunction();
    }

    public static Specification<Product> hasCategory(String category) {
        return (root, query, criteriaBuilder) -> category != null ? criteriaBuilder.equal(root.get("category"), category) : criteriaBuilder.conjunction();
    }

    public static Specification<Product> hasGender(String gender) {
        return (root, query, criteriaBuilder) -> gender != null ? criteriaBuilder.equal(root.get("gender"), gender) : criteriaBuilder.conjunction();
    }

    public static Specification<Product> hasPriceLessThanOrEqual(Double price) {
        return (root, query, criteriaBuilder) -> price != null ? criteriaBuilder.lessThanOrEqualTo(root.get("price"), price) : criteriaBuilder.conjunction();
    }

    public static Specification<Product> isInStock(Boolean inStock) {
        return (root, query, criteriaBuilder) -> inStock != null && inStock ? criteriaBuilder.isTrue(root.get("isinstock")) : criteriaBuilder.conjunction();
    }


    public static Specification<Product> hasProductionDate(String productiondate) {
        return (root, query, criteriaBuilder) -> {
            if (productiondate != null) {
                LocalDateTime ldt = LocalDateTime.parse(productiondate, DATE_TIME_FORMATTER);
                return criteriaBuilder.equal(root.get("productiondate"), ldt);
            }
            return criteriaBuilder.conjunction();
        };
    }
        public static Specification<Product> hasSearchTerm (String search){
            return (root, query, criteriaBuilder) -> search != null ? criteriaBuilder.like(root.get("name"), "%" + search + "%") : criteriaBuilder.conjunction();
        }

}
