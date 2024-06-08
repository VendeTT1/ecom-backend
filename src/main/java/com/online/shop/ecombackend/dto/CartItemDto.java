package com.online.shop.ecombackend.dto;

import com.online.shop.ecombackend.model.Product;

public class CartItemDto {
    private int productId;
    private String title;
    private String image;
    private int rating;
    private double price;
    private String brandname;
    private int amount;
    private String selectedsize;
    private boolean isinwishlist;

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }


    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getBrandname() {
        return brandname;
    }

    public void setBrandname(String brandname) {
        this.brandname = brandname;
    }

    public String getSelectedsize() {
        return selectedsize;
    }

    public void setSelectedsize(String selectedsize) {
        this.selectedsize = selectedsize;
    }

    public boolean isisinwishlist() {
        return isinwishlist;
    }

    public void setIsinwishlist(boolean isinwishlist) {
        this.isinwishlist = isinwishlist;
    }
}
