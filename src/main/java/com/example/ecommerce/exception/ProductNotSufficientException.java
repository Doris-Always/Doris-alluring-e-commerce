package com.example.ecommerce.exception;

public class ProductNotSufficientException extends RuntimeException{

    public ProductNotSufficientException(String message){
        super(message);
    }
}
