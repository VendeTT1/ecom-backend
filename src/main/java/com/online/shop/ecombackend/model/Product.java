package com.online.shop.ecombackend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;


import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Entity
@Table(name = "products")
public class Product {

    @Id
    @Column(name = "id")
    private int id;

    @Column(name = "name", nullable = false, length = 255)
    private String name;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "isinstock")
    private Boolean isinstock;

    @Column(name = "gender", nullable = false, length = 10)
    private String gender;

    @Column(name = "category", nullable = false, length = 50)
    private String category;

    @Column(name = "availablesizes", nullable = false)
//    @Convert(converter = ListToJsonConverter.class)
    private String availablesizes;

    public String getAvailablesizes() {
        return availablesizes;
    }

    public void setAvailablesizes(String availablesizes) {
        this.availablesizes = availablesizes;
    }

    @Column(name = "rating")
    private Float rating;

    @JsonIgnore
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Review> reviews;

    public Review[] getReviews() {
        return reviews == null ? new Review[0] : reviews.toArray(new Review[0]);
    }

    public void setReviews(Review[] reviewsArray) {
        this.reviews = reviewsArray == null ? null : Arrays.asList(reviewsArray);
    }

    @Column(name = "totalreviewcount")
    private Integer totalreviewcount;


    @Column(name = "productiondate", nullable = false)
    private LocalDateTime productiondate;

    @Column(name = "price", nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @Column(name = "brandname", nullable = false, length = 100)
    private String brandname;


    @Column(name = "productcode", nullable = false)
    private Long productcode;


    @Column(name = "imageurl", nullable = false, length = 255)
    private String imageurl;


    @Column(name = "additionalimageurls", columnDefinition = "json")
    @Convert(converter = ListToJsonConverter.class)
    private List<String> additionalimageurls;

    public List<String> getAdditionalimageurls() {
        return additionalimageurls;
    }

    public void setAdditionalimageurls(List<String> additionalimageurls) {
        this.additionalimageurls = additionalimageurls;
    }

    // Getters and setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getBrandname() {
        return brandname;
    }

    public void setBrandname(String brandname) {
        this.brandname = brandname;
    }


    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Boolean getIsinstock() {
        return isinstock;
    }

    public void setIsinstock(Boolean isinstock) {
        this.isinstock = isinstock;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Long getProductcode() {
        return productcode;
    }

    public void setProductcode(Long productcode) {
        this.productcode = productcode;
    }

    public LocalDateTime getProductiondate() {
        return productiondate;
    }

    public void setProductiondate(LocalDateTime productiondate) {
        this.productiondate = productiondate;
    }

    public Float getRating() {
        return rating;
    }

    public void setRating(Float rating) {
        this.rating = rating;
    }

    public Integer getTotalreviewcount() {
        return totalreviewcount;
    }

    public void setTotalreviewcount(Integer totalreviewcount) {
        this.totalreviewcount = totalreviewcount;
    }
}