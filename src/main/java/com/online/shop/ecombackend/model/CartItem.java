package com.online.shop.ecombackend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;


@Entity
@Table(name = "cartitem")
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "productId", nullable = false)
    private int productId;

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }
    @Column(name = "title", nullable = false)
    private String title;
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    @Column(name = "image", nullable = false)
    private String image;
    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
    @Column(name = "rating", nullable = false)
    private int rating;
    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
    @Column(name = "price", nullable = false)
    private double price;
    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Column(name = "brandname", nullable = false)
    private String brandname;

    public String getBrandname() {
        return brandname;
    }

    public void setBrandname(String brandname) {
        this.brandname = brandname;
    }

    @Column(name = "amount", nullable = false)
    private int amount;

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    @Column(name = "selectedsize", nullable = false)
    private String selectedsize;

    public String getSelectedsize() {
        return selectedsize;
    }

    public void setSelectedsize(String selectedsize) {
        this.selectedsize = selectedsize;
    }

    @Column(name = "isinwishlist", nullable = false)
    private boolean isinwishlist;

    public boolean isisinwishlist() {
        return isinwishlist;
    }

    public void setIsinwishlist(boolean isinwishlist) {
        this.isinwishlist = isinwishlist;
    }

    @ManyToOne(optional = false)
    @JoinColumn(name = "order_id", nullable = false)
    @JsonIgnore
    private Order orders;
    public Order getOrders() {
        return orders;
    }

    public void setOrders(Order orders) {
        this.orders = orders;
    }
}
