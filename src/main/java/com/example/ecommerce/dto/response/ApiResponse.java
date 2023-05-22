package com.example.ecommerce.dto.response;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ApiResponse <T>{
    private String message;
    private LocalDateTime timeStamp;
    private T data;

    public ApiResponse(String message, LocalDateTime timeStamp, T data) {
        this.message = message;
        this.timeStamp = timeStamp;
        this.data = data;
    }

    public ApiResponse(String message, LocalDateTime timeStamp) {
        this.message = message;
        this.timeStamp = timeStamp;
    }
}
