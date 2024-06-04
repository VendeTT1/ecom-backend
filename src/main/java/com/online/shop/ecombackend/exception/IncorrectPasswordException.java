package com.online.shop.ecombackend.exception;

public class IncorrectPasswordException extends  RuntimeException{
    public IncorrectPasswordException(String message) {
        super( message);
    }
}
