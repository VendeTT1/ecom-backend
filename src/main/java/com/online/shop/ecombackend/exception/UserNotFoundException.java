package com.online.shop.ecombackend.exception;

public class UserNotFoundException extends RuntimeException{
    public UserNotFoundException(String message) {
        super( message);
    }
}
