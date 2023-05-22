package com.example.ecommerce.exception;

public class EmailNotSentException extends RuntimeException{
    public EmailNotSentException(String message){
        super(message);
    }
}
