package com.online.shop.ecombackend.dto;

import java.util.List;

public class OrderDto {
    private String userId;
    private String orderstatus;
    private double subtotal;
    private List<CartItemDto> cartItem;


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getOrderstatus() {
        return orderstatus;
    }

    public void setOrderstatus(String orderstatus) {
        this.orderstatus = orderstatus;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }

    public List<CartItemDto> getCartItem() {
        return cartItem;
    }

    public void setCartItem(List<CartItemDto> cartItem) {
        this.cartItem = cartItem;
    }


}
